package com.ranjeet.communicationschedulerservice.entity;

import com.ranjeet.communicationschedulerservice.enums.JobStatus;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "job_details")
public class JobDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;

    @Column(name = "task_id")
    Integer taskId;

    @Column(name = "cron_expression")
    String cronExpression;

    @Column(name = "next_run_epoch")
    long nextRunEpoch;

    @Column(name = "job_status")
    @Enumerated(EnumType.STRING)
    JobStatus jobStatus;

    @Column(name = "retry_count")
    Integer retryCount;
}
