package com.ranjeet.communicationschedulerservice.respository;

import com.ranjeet.communicationschedulerservice.entity.JobDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface JobDetailsRepository extends JpaRepository<JobDetails,Integer> {
    @Query(value = "SELECT * FROM job_details jd WHERE jd.next_run_epoch < UNIX_TIMESTAMP() AND jd.job_status NOT IN ('RUNNING','FAILED') FOR UPDATE",nativeQuery = true)
    List<JobDetails> getNextJobsToRun();

    @Query(value = "SELECT * FROM job_details jd WHERE jd.next_run_epoch < UNIX_TIMESTAMP() AND jd.job_status NOT IN ('RUNNING','FAILED') LIMIT :no_of_jobs FOR UPDATE",nativeQuery = true)
    List<JobDetails> getNextNJobsToRun(@Param("no_of_jobs") int onOfJobs);

    @Query(value = "SELECT * FROM job_details jd WHERE jd.next_run_epoch < UNIX_TIMESTAMP() AND jd.job_status ='FAILED' AND retry_count < :max_retry_count FOR UPDATE",nativeQuery = true)
    List<JobDetails> getNextFailedJobsToRun(@Param("max_retry_count") int maxRetryCount);

    @Query(value = "SELECT * FROM job_details jd WHERE jd.next_run_epoch < UNIX_TIMESTAMP() AND jd.job_status ='FAILED' AND retry_count < :max_retry_count LIMIT :no_of_jobs FOR UPDATE",nativeQuery = true)
    List<JobDetails> getNextNFailedJobsToRun(@Param("no_of_jobs") int onOfJobs,@Param("max_retry_count") int maxRetryCount);

    JobDetails getJobByTaskId(Integer taskId);
}
