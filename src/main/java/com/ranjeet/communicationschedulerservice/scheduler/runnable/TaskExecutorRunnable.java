package com.ranjeet.communicationschedulerservice.scheduler.runnable;

import com.ranjeet.communicationschedulerservice.entity.JobDetails;
import com.ranjeet.communicationschedulerservice.enums.JobStatus;
import com.ranjeet.communicationschedulerservice.service.JobDetailsService;
import com.ranjeet.communicationschedulerservice.service.TaskDetailsService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Setter
public class TaskExecutorRunnable implements Runnable{

    JobDetails jobDetails;

    JobDetailsService jobDetailsService;

    TaskDetailsService taskDetailsService;

    @Override
    public void run() {
        log.info("Processing Job with ID {}",jobDetails.getId());
        //does the job

        //update the status along with next epoch time
        jobDetailsService.updateJobDetails(jobDetails.getTaskId(),JobStatus.SUCCESS);

        log.info("Processing Done with ID {}",jobDetails.getId());
    }
}
