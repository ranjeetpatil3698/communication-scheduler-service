package com.ranjeet.communicationschedulerservice.service.impl;

import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.time.ExecutionTime;
import com.cronutils.parser.CronParser;
import com.ranjeet.communicationschedulerservice.entity.JobDetails;
import com.ranjeet.communicationschedulerservice.entity.TaskDetails;
import com.ranjeet.communicationschedulerservice.enums.JobStatus;
import com.ranjeet.communicationschedulerservice.respository.JobDetailsRepository;
import com.ranjeet.communicationschedulerservice.service.JobDetailsService;
import com.ranjeet.communicationschedulerservice.service.TaskDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Component
@Slf4j
public class JobDetailsServiceImpl implements JobDetailsService {
    @Autowired
    JobDetailsRepository jobDetailsRepository;
    @Lazy
    @Autowired
    TaskDetailsService taskDetailsService;

    @Override
    public void saveJobDetails(TaskDetails taskDetails) {
        long epochSecond = getNextEpochSecond(taskDetails);
        JobDetails jobDetails = getJobDetails(taskDetails, epochSecond);
        jobDetailsRepository.save(jobDetails);
    }

    @Override
    @Transactional
    public List<JobDetails> getNextJobsToProcess() {
        List<JobDetails> nextJobToRun = jobDetailsRepository.getNextJobsToRun();
        nextJobToRun.forEach(jobDetails -> jobDetails.setJobStatus(JobStatus.RUNNING));
        return jobDetailsRepository.saveAll(nextJobToRun);
    }


    private long getNextEpochSecond(TaskDetails taskDetails) {
        String cronExpression = taskDetails.getCronExpression();
        ZonedDateTime nextRun = getZonedDateTime(cronExpression);
        return nextRun.toEpochSecond();
    }

    private ZonedDateTime getZonedDateTime(String cronExpression) {
        CronParser parser = new CronParser(CronDefinitionBuilder.instanceDefinitionFor(CronType.UNIX));
        Cron cron = parser.parse(cronExpression);
        ZonedDateTime now = ZonedDateTime.now();
        ExecutionTime executionTime = ExecutionTime.forCron(cron);
        return executionTime.nextExecution(now).get();
    }

    @Override
    public void saveJobDetails(JobDetails jobDetails) {
        log.info("Saving jobDetails {}",jobDetails);
        jobDetailsRepository.save(jobDetails);
    }

    @Override
    @Transactional
    public void updateJobDetails(int taskId, JobStatus jobStatus) {
        TaskDetails taskDetails = taskDetailsService.getTaskDetails(taskId);
        JobDetails jobDetails = jobDetailsRepository.getJobByTaskId(taskId);
        jobDetails.setJobStatus(jobStatus);
        jobDetails.setLastRunTime(LocalDateTime.now());
        jobDetails.setNextRunEpoch(getNextEpochSecond(taskDetails));
        jobDetails.setNextRunTime(getNextRunFromCronExpression(taskDetails.getCronExpression()));
        jobDetailsRepository.save(jobDetails);
    }

    private LocalDateTime getNextRunFromCronExpression(String cronExpression){
        ZonedDateTime nextRun = getZonedDateTime(cronExpression);
        return nextRun.toLocalDateTime();
    }

    private JobDetails getJobDetails(TaskDetails taskDetails, long epochSecond) {
        return JobDetails.builder()
                .jobStatus(JobStatus.SUCCESS)
                .taskId(taskDetails.getId())
                .cronExpression(taskDetails.getCronExpression())
                .lastRunTime(null)
                .nextRunTime(getNextRunFromCronExpression(taskDetails.getCronExpression()))
                .nextRunEpoch(epochSecond)
                .retryCount(0)
                .build();
    }


}
