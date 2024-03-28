package com.ranjeet.communicationschedulerservice.sender;

import com.ranjeet.communicationschedulerservice.enums.CommunicationSenderProvider;

public interface CommunicationSenderFactory {
    CommunicationSender getCommunicationSender(CommunicationSenderProvider provider);
}
