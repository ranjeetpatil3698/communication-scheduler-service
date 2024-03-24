package com.ranjeet.communicationschedulerservice;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResponseDto {
    int statusCode;
    String status;
}
