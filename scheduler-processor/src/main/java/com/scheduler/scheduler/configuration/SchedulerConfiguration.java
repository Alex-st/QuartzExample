package com.scheduler.scheduler.configuration;

import com.scheduler.scheduler.properties.SchedulerConfigurationProperties;
import com.scheduler.scheduler.quartz.LoggingQuartzJobListener;
import com.scheduler.scheduler.quartz.job.TestJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobListener;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.quartz.CronScheduleBuilder.cronSchedule;

@Configuration
@EnableConfigurationProperties({SchedulerConfigurationProperties.class})
public class SchedulerConfiguration {
    @Bean
    public JobListener getJobListener() {
        return new LoggingQuartzJobListener();
    }

    @Bean
    public JobDetail getJobDetail() {
        return JobBuilder.newJob().ofType(TestJob.class)
                .storeDurably()
                .withIdentity("Test_Job")
                .withDescription("Invoke processing of test job.")
                .build();
    }

    @Bean
    public Trigger getJobTrigger(JobDetail jobDetail, SchedulerConfigurationProperties properties) {
        return TriggerBuilder.newTrigger().forJob(jobDetail)
                .withIdentity("Process_Test_Job_Trigger")
                .withDescription("Invoke processing of test job trigger.")
                .withSchedule(cronSchedule(properties.getCronTemplate()))
                .build();
    }
}
