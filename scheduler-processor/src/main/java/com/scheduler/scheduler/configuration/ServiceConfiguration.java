package com.scheduler.scheduler.configuration;

import com.scheduler.scheduler.service.SomeService;
import com.scheduler.scheduler.service.SomeServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {
    @Bean
    public SomeService someService() {
        return new SomeServiceImpl();
    }
}
