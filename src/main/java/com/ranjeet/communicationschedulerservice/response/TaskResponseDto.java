package com.ranjeet.communicationschedulerservice.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TaskResponseDto {
    @Schema(description = "Id Of task")
    Integer id;
    @Schema(description = "From Name")
    String sender;
    @Schema(description = "Email recipient")
    String communicationAddress;
    @Schema(description = "Email message")
    String message;
    @Schema(description = "Scheduled delivery Time In UNIX Cron Syntax")
    String cronExpression;
}
