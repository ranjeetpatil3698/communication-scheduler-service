package com.ranjeet.communicationschedulerservice.service;

import com.ranjeet.communicationschedulerservice.Exception.TaskDetailsNotFoundException;
import com.ranjeet.communicationschedulerservice.entity.TaskDetails;
import com.ranjeet.communicationschedulerservice.request.TaskRequestDto;
import com.ranjeet.communicationschedulerservice.response.TaskResponseDto;



public interface TaskDetailsService {
    TaskDetails addTask(TaskDetails taskDetails);
    TaskResponseDto transform(TaskDetails taskDetails);
    TaskDetails transform(TaskRequestDto taskRequestDto);
    TaskDetails getTaskDetails(Integer id);
}
