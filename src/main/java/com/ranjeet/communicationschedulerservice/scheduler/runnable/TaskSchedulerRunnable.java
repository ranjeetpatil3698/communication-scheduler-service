package com.ranjeet.communicationschedulerservice.scheduler.runnable;

import com.ranjeet.communicationschedulerservice.entity.JobDetails;
import com.ranjeet.communicationschedulerservice.sender.CommunicationSender;
import com.ranjeet.communicationschedulerservice.service.JobDetailsService;
import com.ranjeet.communicationschedulerservice.service.TaskDetailsService;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Setter
public class TaskSchedulerRunnable implements Runnable{

    JobDetailsService jobDetailsService;

    TaskDetailsService taskDetailsService;

    ExecutorService executor;

    MeterRegistry meterRegistry;

    CommunicationSender communicationSender;

    public TaskSchedulerRunnable(JobDetailsService jobDetailsService, TaskDetailsService taskDetailsService, MeterRegistry meterRegistry, Integer noOfJobsExecutorThreads, CommunicationSender communicationSender) {
        this.jobDetailsService = jobDetailsService;
        this.taskDetailsService = taskDetailsService;
        this.meterRegistry = meterRegistry;
        this.communicationSender = communicationSender;
        executor =  Executors.newFixedThreadPool(noOfJobsExecutorThreads);
    }

    @Override
    public void run() {
        List<JobDetails> nextJobsToProcess = jobDetailsService.getNextJobsToProcess();

        if(!nextJobsToProcess.isEmpty()){
            log.info("Next Job to process {} ",nextJobsToProcess);
            for(JobDetails jobDetails : nextJobsToProcess){
                TaskExecutorRunnable taskExecutorRunnable = new TaskExecutorRunnable(jobDetails,jobDetailsService,taskDetailsService,meterRegistry,communicationSender);
                executor.submit(taskExecutorRunnable);
            }
        }
    }
}
