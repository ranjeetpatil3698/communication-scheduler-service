package com.ranjeet.communicationschedulerservice.sender.impl;

import com.ranjeet.communicationschedulerservice.entity.JobDetails;
import com.ranjeet.communicationschedulerservice.entity.TaskDetails;
import com.ranjeet.communicationschedulerservice.request.SimulateRequestDto;
import com.ranjeet.communicationschedulerservice.response.SimulateResponseDto;
import com.ranjeet.communicationschedulerservice.sender.CommunicationSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
@Qualifier("SIMULATE")
public class SimulateSenderImpl implements CommunicationSender {

    @Value("${senderProviderConfiguration.simulate.host}")
    String senderHost;
    @Value("${senderProviderConfiguration.simulate.port}")
    String senderPort;
    @Value("${senderProviderConfiguration.simulate.url}")
    String senderUrl;
    private final RestClient restClient = RestClient
            .builder()
            .baseUrl("http://127.0.0.1:8081").build();

    @Override
    public void sendCommunication(TaskDetails taskDetails, JobDetails jobDetails) {

        SimulateRequestDto simulateRequestDto = getRequestDto(taskDetails);

        ResponseEntity<SimulateResponseDto> response =  makeHttpRequest(simulateRequestDto);

    }

    private ResponseEntity<SimulateResponseDto> makeHttpRequest(SimulateRequestDto simulateRequestDto) {
        return restClient
                    .post()
                    .uri("/send/communication")
                    .body(simulateRequestDto)
                    .retrieve()
                    .toEntity(SimulateResponseDto.class);
    }

    private static SimulateRequestDto getRequestDto(TaskDetails taskDetails) {
        return SimulateRequestDto
                .builder()
                .message(taskDetails.getMessage())
                .communicationAddress(taskDetails.getCommunicationAddress())
                .sender(taskDetails.getSender()).build();
    }
}
