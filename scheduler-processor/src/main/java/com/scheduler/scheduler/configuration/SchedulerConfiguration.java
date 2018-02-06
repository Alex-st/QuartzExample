package com.scheduler.scheduler.configuration;

import com.scheduler.scheduler.properties.SchedulerConfigurationProperties;
import com.scheduler.scheduler.quartz.AutowiringSpringBeanJobFactory;
import com.scheduler.scheduler.quartz.LoggingQuartzJobListener;
import com.scheduler.scheduler.quartz.job.TestJob;
import org.quartz.*;
import org.quartz.spi.JobFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

import static org.apache.commons.collections4.MapUtils.toProperties;
import static org.quartz.CronScheduleBuilder.cronSchedule;

@Configuration
@EnableConfigurationProperties({SchedulerConfigurationProperties.class})
public class SchedulerConfiguration {
    @Bean
    public JobFactory jobFactory() {
        AutowiringSpringBeanJobFactory autowiringSpringBeanJobFactory = new AutowiringSpringBeanJobFactory();
        autowiringSpringBeanJobFactory.setIgnoredUnknownProperties("applicationContext");
        return autowiringSpringBeanJobFactory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource,
                                                     JobFactory jobFactory,
                                                     SchedulerConfigurationProperties schedulerProperties) {
        JobDetail jobDetail = getJobDetail();
        Trigger jobTrigger = getJobTrigger(jobDetail, schedulerProperties);

        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setOverwriteExistingJobs(true);
        factory.setDataSource(dataSource);
        factory.setTriggers(jobTrigger);
        factory.setJobDetails(jobDetail);
        factory.setJobFactory(jobFactory);
        factory.setApplicationContextSchedulerContextKey("applicationContext");
        Properties quartzProperties = toProperties(schedulerProperties.getQuartz());
        factory.setQuartzProperties(quartzProperties);
        factory.setGlobalJobListeners(getJobListener());
        return factory;
    }

    private JobListener getJobListener() {
        return new LoggingQuartzJobListener();
    }

    private JobDetail getJobDetail() {
        return JobBuilder.newJob().ofType(TestJob.class)
                .storeDurably()
                .withIdentity("Test_Job")
                .withDescription("Invoke processing of test job.")
                .build();
    }

    private Trigger getJobTrigger(JobDetail jobDetail, SchedulerConfigurationProperties properties) {
        return TriggerBuilder.newTrigger().forJob(jobDetail)
                .withIdentity("Process_Test_Job_Trigger")
                .withDescription("Invoke processing of test job trigger.")
                .withSchedule(cronSchedule(properties.getCronTemplate()))
                .build();
    }
}
