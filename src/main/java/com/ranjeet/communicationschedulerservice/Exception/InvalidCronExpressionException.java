package com.ranjeet.communicationschedulerservice.Exception;

public class InvalidCronExpressionException extends Exception{
    public InvalidCronExpressionException(String message) {
        super(message);
    }
}
