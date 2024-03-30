package com.ranjeet.communicationschedulerservice.scheduler.runnable;

import com.ranjeet.communicationschedulerservice.entity.JobDetails;
import com.ranjeet.communicationschedulerservice.entity.TaskDetails;
import com.ranjeet.communicationschedulerservice.enums.JobStatus;
import com.ranjeet.communicationschedulerservice.sender.CommunicationSender;
import com.ranjeet.communicationschedulerservice.service.JobDetailsService;
import com.ranjeet.communicationschedulerservice.service.TaskDetailsService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;


@Slf4j
@Setter
public class TaskExecutorRunnable implements Runnable{

    JobDetails jobDetails;

    JobDetailsService jobDetailsService;

    TaskDetailsService taskDetailsService;

    Counter jobProcessedCounter;

    Timer jobProcessingLatency;

    MeterRegistry meterRegistry;

    CommunicationSender communicationSender;


    public TaskExecutorRunnable(JobDetails jobDetails, JobDetailsService jobDetailsService, TaskDetailsService taskDetailsService, MeterRegistry meterRegistry, CommunicationSender communicationSender) {
        this.jobDetails = jobDetails;
        this.jobDetailsService = jobDetailsService;
        this.taskDetailsService = taskDetailsService;
        this.meterRegistry = meterRegistry;
        this.communicationSender = communicationSender;
        jobProcessedCounter = meterRegistry.counter("job_processed");
        jobProcessingLatency = meterRegistry.timer("job.processing.latency");
    }


    @Override
    public void run() {
        Timer.Sample processingLatency = Timer.start();
        log.info("Processing Job with ID {} on {}",jobDetails.getId(),LocalDateTime.now());
        JobStatus currentJobStatus = JobStatus.RUNNING;
        try{
            TaskDetails taskDetails = taskDetailsService.getTaskDetails(jobDetails.getTaskId());
            communicationSender.sendCommunication(taskDetails,jobDetails);
            jobDetailsService.updateJobDetails(jobDetails.getTaskId(),JobStatus.SUCCESS,jobDetails.getRetryCount());
            currentJobStatus = JobStatus.SUCCESS;
            log.info("Processing Done with ID {}",jobDetails.getId());
        }catch (Exception exception){
            log.error("Job Failed with Id {} with Exception {}",jobDetails.getId(),exception.getMessage());
            jobDetailsService.updateJobDetails(jobDetails.getTaskId(),JobStatus.FAILED,jobDetails.getRetryCount());
            currentJobStatus = JobStatus.FAILED;
        }finally {
            if(currentJobStatus == JobStatus.RUNNING){
                jobDetailsService.updateJobDetails(jobDetails.getTaskId(),JobStatus.FAILED,jobDetails.getRetryCount());
            }
            jobProcessedCounter.increment();
            processingLatency.stop(jobProcessingLatency);
        }
    }
}
