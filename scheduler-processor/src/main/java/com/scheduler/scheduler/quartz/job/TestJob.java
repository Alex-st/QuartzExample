package com.scheduler.scheduler.quartz.job;

import com.scheduler.scheduler.service.SomeService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

@Slf4j
public class TestJob extends QuartzJobBean {

    @Autowired
    private SomeService someService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("Running scheduled job...");
        someService.printMessage("testjob message");
    }
}
