package com.ranjeet.communicationschedulerservice.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


//{
//        "sender":"ranjeet@patil.com",
//        "communicationAddress":"patilranjeet3699@gmail.com",
//        "message":"hello"
//}

@Getter
@Setter
@Builder
public class SimulateRequestDto {
    String sender;
    String communicationAddress;
    String message;
}
