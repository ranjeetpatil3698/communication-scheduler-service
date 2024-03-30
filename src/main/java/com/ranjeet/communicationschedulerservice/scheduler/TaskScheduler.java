package com.ranjeet.communicationschedulerservice.scheduler;

import com.ranjeet.communicationschedulerservice.Exception.CommunicationProviderNotFoundProviderException;
import com.ranjeet.communicationschedulerservice.enums.CommunicationSenderProvider;
import com.ranjeet.communicationschedulerservice.scheduler.runnable.TaskSchedulerRunnable;
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
public class TaskScheduler {

    @Autowired
    JobDetailsService jobDetailsService;

    @Autowired
    TaskDetailsService taskDetailsService;

    @Autowired
    MeterRegistry meterRegistry;

    @Autowired
    CommunicationSenderFactory communicationSenderFactory;

    @Value("${scheduler.noOfTaskSchedulerThreads}")
    int taskSchedulerMaximumThreads;

    @Value("${scheduler.startTaskScheduler}")
    Boolean startTaskScheduler;

    @Value("${scheduler.noOfJobsExecutorThreads}")
    Integer noOfJobsExecutorThreads;

    @Value("${sender.provider}")
    CommunicationSenderProvider senderProvider;
    ScheduledExecutorService scheduledExecutorService;

    public TaskScheduler() {
        scheduledExecutorService = Executors.newScheduledThreadPool(taskSchedulerMaximumThreads);
    }

    @PostConstruct
    public void start() throws CommunicationProviderNotFoundProviderException {
        CommunicationSender communicationSender = communicationSenderFactory.getCommunicationSender(senderProvider);
        if(Objects.isNull(communicationSender)){
            throw new CommunicationProviderNotFoundProviderException("Communication Provider Not Found");
        }
        if(Objects.nonNull(startTaskScheduler) && startTaskScheduler){
            log.info("Starting task scheduler");
            for(int i = 0; i < taskSchedulerMaximumThreads; i++){
                TaskSchedulerRunnable taskSchedulerRunnable = new TaskSchedulerRunnable(jobDetailsService,taskDetailsService,meterRegistry,noOfJobsExecutorThreads, communicationSender);
                scheduledExecutorService.scheduleAtFixedRate(taskSchedulerRunnable,1,1, TimeUnit.SECONDS);
            }
        }
    }


    @PreDestroy
    public void stop() {
        if(Objects.nonNull(startTaskScheduler) && startTaskScheduler){
            log.info("Stopping task scheduler");
            scheduledExecutorService.shutdown();
        }
    }
}

/*
*
* */
