package com.ranjeet.communicationschedulerservice.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskRequestDto {
    String sender;
    String communicationAddress;
    String message;
    String cronExpression;
}
