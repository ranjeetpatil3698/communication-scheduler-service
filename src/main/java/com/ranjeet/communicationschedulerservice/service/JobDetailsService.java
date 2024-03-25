package com.ranjeet.communicationschedulerservice.service;

import com.ranjeet.communicationschedulerservice.entity.JobDetails;
import com.ranjeet.communicationschedulerservice.entity.TaskDetails;
import com.ranjeet.communicationschedulerservice.enums.JobStatus;

import java.util.List;

public interface JobDetailsService {
    void saveJobDetails(TaskDetails taskDetails);
    List<JobDetails> getNextJobsToProcess();

    void saveJobDetails(JobDetails jobDetails);
    void updateJobDetails(int taskId, JobStatus jobStatus);
}
