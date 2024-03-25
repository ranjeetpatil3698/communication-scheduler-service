package com.ranjeet.communicationschedulerservice.scheduler;

import com.ranjeet.communicationschedulerservice.scheduler.runnable.TaskSchedulerRunnable;
import com.ranjeet.communicationschedulerservice.service.JobDetailsService;
import com.ranjeet.communicationschedulerservice.service.TaskDetailsService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

    @Value("${scheduler.noOfTaskSchedulerThreads}")
    int taskSchedulerMaximumThreads;

    private final Counter jobProccesedCounter;

    private final Timer jobProcessingLatency;

    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(taskSchedulerMaximumThreads);

    public TaskScheduler(@Autowired MeterRegistry meterRegistry) {
        jobProccesedCounter = meterRegistry.counter("job_processed");
        jobProcessingLatency = meterRegistry.timer("job.processing.latency");
    }

    @PostConstruct
    public void start() {
        log.info("Starting task scheduler");
        for(int i = 0; i < taskSchedulerMaximumThreads; i++){
            TaskSchedulerRunnable taskSchedulerRunnable = new TaskSchedulerRunnable();
            taskSchedulerRunnable.setJobDetailsService(jobDetailsService);
            taskSchedulerRunnable.setTaskDetailsService(taskDetailsService);
            taskSchedulerRunnable.setJobProcessedCounter(jobProccesedCounter);
            taskSchedulerRunnable.setJobProcessingLatency(jobProcessingLatency);
            scheduledExecutorService.scheduleAtFixedRate(taskSchedulerRunnable,1,1, TimeUnit.SECONDS);
        }
    }


    @PreDestroy
    public void stop() {
        log.info("Stopping task scheduler");
        scheduledExecutorService.shutdown();
    }
}
