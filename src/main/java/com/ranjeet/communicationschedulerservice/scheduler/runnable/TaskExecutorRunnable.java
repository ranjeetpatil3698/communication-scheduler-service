package com.ranjeet.communicationschedulerservice.scheduler.runnable;

import com.ranjeet.communicationschedulerservice.entity.JobDetails;
import com.ranjeet.communicationschedulerservice.enums.JobStatus;
import com.ranjeet.communicationschedulerservice.service.JobDetailsService;
import com.ranjeet.communicationschedulerservice.service.TaskDetailsService;
import io.micrometer.core.instrument.Counter;
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

    @Override
    public void run() {
        Timer.Sample processingLatency = Timer.start();
        log.info("Processing Job with ID {} on {}",jobDetails.getId(),LocalDateTime.now());
        //does the job

        //update the status along with next epoch time
        jobDetailsService.updateJobDetails(jobDetails.getTaskId(),JobStatus.SUCCESS);

        jobProcessedCounter.increment();
        processingLatency.stop(jobProcessingLatency);
        log.info("Processing Done with ID {}",jobDetails.getId());
    }
}
