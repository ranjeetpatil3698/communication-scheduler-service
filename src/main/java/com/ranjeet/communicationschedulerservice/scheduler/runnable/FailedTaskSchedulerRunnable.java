package com.ranjeet.communicationschedulerservice.scheduler.runnable;

import com.ranjeet.communicationschedulerservice.entity.JobDetails;
import com.ranjeet.communicationschedulerservice.sender.CommunicationSender;
import com.ranjeet.communicationschedulerservice.service.JobDetailsService;
import com.ranjeet.communicationschedulerservice.service.TaskDetailsService;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class FailedTaskSchedulerRunnable implements Runnable{

    JobDetailsService jobDetailsService;

    TaskDetailsService taskDetailsService;

    ExecutorService executor;

    MeterRegistry meterRegistry;

    CommunicationSender communicationSender;

    public FailedTaskSchedulerRunnable(JobDetailsService jobDetailsService, TaskDetailsService taskDetailsService, MeterRegistry meterRegistry,Integer noOfFailedJobsExecutorThreads,CommunicationSender communicationSender) {
        this.jobDetailsService = jobDetailsService;
        this.taskDetailsService = taskDetailsService;
        this.meterRegistry = meterRegistry;
        this.communicationSender = communicationSender;
        executor = Executors.newFixedThreadPool(noOfFailedJobsExecutorThreads);
    }

    @Override
    public void run() {
        List<JobDetails> nextJobsToProcess = jobDetailsService.getNextFailedJobsToProcess();

        if(!nextJobsToProcess.isEmpty()){

            log.info("Next Failed Job to process {} ",nextJobsToProcess);

            for(JobDetails jobDetails : nextJobsToProcess){
                FailedTaskExecutorRunnable failedTaskExecutorRunnable = new FailedTaskExecutorRunnable(jobDetails,jobDetailsService,taskDetailsService,meterRegistry,communicationSender);
                executor.submit(failedTaskExecutorRunnable);
            }
        }
    }
}
