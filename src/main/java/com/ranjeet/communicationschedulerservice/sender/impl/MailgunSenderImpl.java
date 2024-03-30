package com.ranjeet.communicationschedulerservice.sender.impl;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.ranjeet.communicationschedulerservice.entity.JobDetails;
import com.ranjeet.communicationschedulerservice.entity.TaskDetails;
import com.ranjeet.communicationschedulerservice.request.MailgunRequestDto;
import com.ranjeet.communicationschedulerservice.sender.CommunicationSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Qualifier("MAILGUN")
public class MailgunSenderImpl implements CommunicationSender {

    @Value("${senderProviderConfiguration.mailgun.host}")
    String senderHost;
    @Value("${senderProviderConfiguration.mailgun.url}")
    String senderUrl;
    @Value("${senderProviderConfiguration.mailgun.sendingDomain}")
    String sendingDomain;
    @Value("${sender.from}")
    String from;
    @Value("${sender.subject}")
    String subject;
    @Value("${senderProviderConfiguration.mailgun.apiKey}")
    String apiKey;


    @Override
    public void sendCommunication(TaskDetails taskDetails, JobDetails jobDetails) {
        MailgunRequestDto mailgunRequestDto = getMailgunRequestDto(taskDetails);
        try{
            JsonNode response = sendSimpleMessage(mailgunRequestDto);
            log.info("Response From Mailgun {}",response);
        }catch (Exception exception){
            log.error("Exception {}",exception.getMessage());
        }
    }

    private MailgunRequestDto getMailgunRequestDto(TaskDetails taskDetails){
        return MailgunRequestDto.builder()
                .from(taskDetails.getSender() + " " + "<" + "reminder@" + sendingDomain + ">")
                .to(taskDetails.getCommunicationAddress())
                .subject(subject)
                .html(taskDetails.getMessage())
                .build();
    }


    public JsonNode sendSimpleMessage(MailgunRequestDto mailgunRequestDto) throws UnirestException {
        log.info("MailgunRequestDto {}",mailgunRequestDto);
        HttpResponse<JsonNode> request = Unirest.post(senderHost + senderUrl)
                                        .basicAuth("api", apiKey)
                                            .queryString("from", mailgunRequestDto.getFrom())
                                            .queryString("to", mailgunRequestDto.getTo())
                                            .queryString("subject", mailgunRequestDto.getSubject())
                                            .queryString("html", mailgunRequestDto.getHtml())
                                            .asJson();
        return request.getBody();
    }

}
