package com.ranjeet.communicationschedulerservice.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDto {
    String message;
}
