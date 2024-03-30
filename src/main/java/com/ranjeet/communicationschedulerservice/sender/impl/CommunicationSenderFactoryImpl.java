package com.ranjeet.communicationschedulerservice.sender.impl;

import com.ranjeet.communicationschedulerservice.enums.CommunicationSenderProvider;
import com.ranjeet.communicationschedulerservice.sender.CommunicationSender;
import com.ranjeet.communicationschedulerservice.sender.CommunicationSenderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CommunicationSenderFactoryImpl implements CommunicationSenderFactory {

    @Autowired
    @Qualifier("SIMULATE")
    CommunicationSender simulateCommunicationSender;

    @Autowired
    @Qualifier("MAILGUN")
    CommunicationSender mailgunCommunicationSender;

     public CommunicationSender getCommunicationSender(CommunicationSenderProvider provider){
        if(provider.equals(CommunicationSenderProvider.SIMULATE)){
            return simulateCommunicationSender;
        }
        if(provider.equals(CommunicationSenderProvider.MAILGUN)){
            return mailgunCommunicationSender;
        }
         return null;
    }
}
