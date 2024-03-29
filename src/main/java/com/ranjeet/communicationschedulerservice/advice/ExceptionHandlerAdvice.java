package com.ranjeet.communicationschedulerservice.advice;

import com.ranjeet.communicationschedulerservice.Exception.CommunicationProviderNotFoundProviderException;
import com.ranjeet.communicationschedulerservice.Exception.InvalidCronExpressionException;
import com.ranjeet.communicationschedulerservice.Exception.TaskDetailsNotFoundException;
import com.ranjeet.communicationschedulerservice.response.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler({Exception.class, CommunicationProviderNotFoundProviderException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception ex) {
        return new ResponseEntity<>(ErrorResponseDto.builder().message(ex.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({InvalidCronExpressionException.class, TaskDetailsNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ErrorResponseDto> handleValidationException(Exception ex) {
        return new ResponseEntity<>(ErrorResponseDto.builder().message(ex.getMessage()).build(), HttpStatus.BAD_REQUEST);
    }

}
