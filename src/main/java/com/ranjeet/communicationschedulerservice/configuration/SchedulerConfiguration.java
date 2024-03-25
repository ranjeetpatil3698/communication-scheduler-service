package com.ranjeet.communicationschedulerservice.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "scheduler")
@Setter
@Getter
public class SchedulerConfiguration {
    private Integer noOfTaskSchedulerThreads;
    private Integer noOfTaskSchedulerRunnableThreads;
}
