package com.ranjeet.communicationschedulerservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@Table(name = "task_details")
@NoArgsConstructor
@AllArgsConstructor
public class TaskDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;

    @Column(name="sender")
    String sender;

    @Column(name="communication_address")
    String communicationAddress;

    @Column(name="message")
    String message;

    @Column(name="cron_expression")
    String cronExpression;
}
