package com.ranjeet.communicationschedulerservice;

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
            RequestDto requestDto = RequestDto
                    .builder()
                    .message("hello")
                    .communicationAddress("patilranjeet3699@gmail.com")
                    .sender("ranjeet@patil.com").build();
            ResponseEntity<ResponseDto> response = null;
            try {
                response = restClient
                        .post()
                        .uri("/send/communication")
                        .body(requestDto)
                        .retrieve()
                        .toEntity(ResponseDto.class);
                log.info("Response {} ", response);
            } catch (RestClientException restClientException) {
                log.error("Error {}", restClientException.getMessage());
            }

        }
    }
}

