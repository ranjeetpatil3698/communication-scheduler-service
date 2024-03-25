package com.ranjeet.communicationschedulerservice.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TaskResponseDto {
    Integer id;
    String sender;
    String communicationAddress;
    String message;
    String cronExpression;
}
