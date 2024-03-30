package com.ranjeet.communicationschedulerservice.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskRequestDto {
    @Schema(description = "From Name")
    String sender;
    @Schema(description = "Email recipient")
    String communicationAddress;
    @Schema(description = "Email message in HTML format")
    String message;
    @Schema(description = "Scheduled delivery Time In UNIX Cron Syntax")
    String cronExpression;
}
