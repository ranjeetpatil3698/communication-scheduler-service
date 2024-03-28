package com.ranjeet.communicationschedulerservice.scheduler;

import com.ranjeet.communicationschedulerservice.Exception.CommunicationProviderNotFoundProviderException;
import com.ranjeet.communicationschedulerservice.enums.CommunicationSenderProvider;
import com.ranjeet.communicationschedulerservice.scheduler.runnable.FailedTaskSchedulerRunnable;
import com.ranjeet.communicationschedulerservice.sender.CommunicationSender;
import com.ranjeet.communicationschedulerservice.sender.CommunicationSenderFactory;
import com.ranjeet.communicationschedulerservice.service.JobDetailsService;
import com.ranjeet.communicationschedulerservice.service.TaskDetailsService;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class FailedTaskScheduler {
    @Autowired
    JobDetailsService jobDetailsService;

    @Autowired
    TaskDetailsService taskDetailsService;

    @Autowired
    MeterRegistry meterRegistry;

    @Autowired
    CommunicationSenderFactory communicationSenderFactory;

    ScheduledExecutorService scheduledExecutorService;

    @Value("${scheduler.noOfFailedTaskSchedulerThreads}")
    int noOfFailedTaskSchedulerThreads;

    @Value("${scheduler.startFailedTaskScheduler}")
    Boolean startFailedTaskScheduler;

    @Value("${scheduler.noOfFailedJobsExecutorThreads}")
    Integer noOfFailedJobsExecutorThreads;

    @Value("${senderProvider}")
    CommunicationSenderProvider senderProvider;

    public FailedTaskScheduler() {
        scheduledExecutorService = Executors.newScheduledThreadPool(noOfFailedTaskSchedulerThreads);
    }

    @PostConstruct
    public void start() throws CommunicationProviderNotFoundProviderException {
        CommunicationSender communicationSender = communicationSenderFactory.getCommunicationSender(senderProvider);
        if(Objects.isNull(communicationSender)){
            throw new CommunicationProviderNotFoundProviderException("Communication Provider Not Found");
        }

        if(Objects.nonNull(startFailedTaskScheduler) && startFailedTaskScheduler){
            log.info("Starting Failed task scheduler");
            for(int i = 0; i < noOfFailedTaskSchedulerThreads; i++){
                FailedTaskSchedulerRunnable failedTaskSchedulerRunnable = new FailedTaskSchedulerRunnable(jobDetailsService,taskDetailsService,meterRegistry,noOfFailedJobsExecutorThreads,communicationSender);
                scheduledExecutorService.scheduleAtFixedRate(failedTaskSchedulerRunnable,1,1, TimeUnit.SECONDS);
            }
        }
    }


    @PreDestroy
    public void stop() {
        log.info("Stopping task scheduler");
        scheduledExecutorService.shutdown();
    }
}
