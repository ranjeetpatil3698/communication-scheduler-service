package com.ranjeet.communicationschedulerservice.service;

import com.ranjeet.communicationschedulerservice.entity.TaskDetails;
import com.ranjeet.communicationschedulerservice.request.TaskRequestDto;
import com.ranjeet.communicationschedulerservice.response.TaskResponseDto;

import java.util.List;


public interface TaskDetailsService {
    TaskDetails addTask(TaskDetails taskDetails);
    TaskResponseDto transform(TaskDetails taskDetails);
    List<TaskResponseDto> transform(List<TaskDetails> taskDetails);
    TaskDetails transform(TaskRequestDto taskRequestDto);
    TaskDetails getTaskDetails(Integer id);
    List<TaskDetails> getTask(Integer taskId);
    void deleteTask(Integer taskId);
}
