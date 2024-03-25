package com.ranjeet.communicationschedulerservice.scheduler.runnable;

import com.ranjeet.communicationschedulerservice.entity.JobDetails;
import com.ranjeet.communicationschedulerservice.service.JobDetailsService;
import com.ranjeet.communicationschedulerservice.service.TaskDetailsService;
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

    ExecutorService executor = Executors.newFixedThreadPool(10);


    @Override
    public void run() {
        if(Thread.interrupted()){
            log.info("Received request for shutdown !! Stopping taskSchedulerService");
            return;
        }

        List<JobDetails> nextJobsToProcess = jobDetailsService.getNextJobsToProcess();

        if(!nextJobsToProcess.isEmpty()){

            log.info("Next Job to process {} ",nextJobsToProcess);

            for(JobDetails jobDetails : nextJobsToProcess){
                TaskExecutorRunnable taskExecutorRunnable = new TaskExecutorRunnable();
                taskExecutorRunnable.setJobDetails(jobDetails);
                taskExecutorRunnable.setTaskDetailsService(taskDetailsService);
                taskExecutorRunnable.setJobDetailsService(jobDetailsService);
                executor.submit(taskExecutorRunnable);
            }
        }
    }
}
