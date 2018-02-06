package com.scheduler.scheduler.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.listeners.JobListenerSupport;

@Slf4j
public class LoggingQuartzJobListener extends JobListenerSupport {

    private static final String QUARTZ_JOB_EXECUTION_LISTENER = "Quartz job execution listener";

    @Override
    public String getName() {
        return QUARTZ_JOB_EXECUTION_LISTENER;
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        String jobName = context.getJobDetail().getKey().toString();
        log.debug("Job : {} is going to start...", jobName);
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        String jobName = context.getJobDetail().getKey().toString();
        log.debug("Job : {} is finished...", jobName);
    }

}
