package com.ranjeet.communicationschedulerservice.sender;

import com.ranjeet.communicationschedulerservice.entity.JobDetails;
import com.ranjeet.communicationschedulerservice.entity.TaskDetails;

public interface CommunicationSender {
    void sendCommunication(TaskDetails taskDetails, JobDetails jobDetails);
}
