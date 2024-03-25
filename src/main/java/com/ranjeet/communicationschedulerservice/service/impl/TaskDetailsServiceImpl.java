package com.ranjeet.communicationschedulerservice.service.impl;

import com.ranjeet.communicationschedulerservice.entity.TaskDetails;
import com.ranjeet.communicationschedulerservice.request.TaskRequestDto;
import com.ranjeet.communicationschedulerservice.response.TaskResponseDto;
import com.ranjeet.communicationschedulerservice.respository.TaskDetailsRepository;
import com.ranjeet.communicationschedulerservice.service.JobDetailsService;
import com.ranjeet.communicationschedulerservice.service.TaskDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class TaskDetailsServiceImpl implements TaskDetailsService {
    @Autowired
    TaskDetailsRepository taskDetailsRepository;
    @Autowired
    JobDetailsService jobDetailsService;


    @Override
    @Transactional
    public TaskDetails addTask(TaskDetails taskDetails) {
        TaskDetails savedTaskDetails = taskDetailsRepository.save(taskDetails);
        jobDetailsService.saveJobDetails(savedTaskDetails);
        return savedTaskDetails;
    }

    @Override
    public TaskResponseDto transform(TaskDetails taskDetails) {
        return TaskResponseDto.builder()
                .id(taskDetails.getId())
                .sender(taskDetails.getSender())
                .communicationAddress(taskDetails.getCommunicationAddress())
                .message(taskDetails.getMessage())
                .cronExpression(taskDetails.getCronExpression())
                .build();
    }

    @Override
    public TaskDetails transform(TaskRequestDto taskRequestDto) {
        return TaskDetails.builder()
                .communicationAddress(taskRequestDto.getCommunicationAddress())
                .sender(taskRequestDto.getSender())
                .message(taskRequestDto.getMessage())
                .cronExpression(taskRequestDto.getCronExpression())
                .build();
    }

    @Override
    public TaskDetails getTaskDetails(Integer id) {
        return taskDetailsRepository.getReferenceById(id);
    }
}
