package com.ranjeet.communicationschedulerservice.scheduler.runnable;

import com.ranjeet.communicationschedulerservice.request.SimulateRequestDto;
import com.ranjeet.communicationschedulerservice.response.SimulateResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Slf4j
public class HttpRunnable implements Runnable {

    private static final RestClient restClient = RestClient
            .builder()
            .baseUrl("http://127.0.0.1:8081").build();


    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            SimulateRequestDto simulateRequestDto = SimulateRequestDto
                    .builder()
                    .message("hello")
                    .communicationAddress("patilranjeet3699@gmail.com")
                    .sender("ranjeet@patil.com").build();
            ResponseEntity<SimulateResponseDto> response;
            try {
                response = restClient
                        .post()
                        .uri("/send/communication")
                        .body(simulateRequestDto)
                        .retrieve()
                        .toEntity(SimulateResponseDto.class);
                log.info("Response {} ", response);
            } catch (RestClientException restClientException) {
                log.error("Error {}", restClientException.getMessage());
            }

        }
    }
}

