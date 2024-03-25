package com.ranjeet.communicationschedulerservice.respository;

import com.ranjeet.communicationschedulerservice.entity.JobDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface JobDetailsRepository extends JpaRepository<JobDetails,Integer> {
    @Query(value = "SELECT * FROM job_details jd WHERE jd.next_run_epoch < UNIX_TIMESTAMP() AND jd.job_status NOT IN ('RUNNING','FAILED') FOR UPDATE",nativeQuery = true)
    List<JobDetails> getNextJobsToRun();

    JobDetails getJobByTaskId(Integer taskId);
}
