package com.ranjeet.communicationschedulerservice.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
public class MailgunRequestDto {
    String from;
    String to;
    String subject;
    String html;
}
