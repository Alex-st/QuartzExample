package com.scheduler.scheduler.properties;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

@Data
@Validated
@ConfigurationProperties("scheduler")
public class SchedulerConfigurationProperties {

    @NotBlank
    private String cronTemplate;

    @NotEmpty
    private Map<String, String> quartz;
}
