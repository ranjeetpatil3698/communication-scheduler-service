package com.ranjeet.communicationschedulerservice.service.impl;

import com.ranjeet.communicationschedulerservice.entity.TaskDetails;
import com.ranjeet.communicationschedulerservice.request.TaskRequestDto;
import com.ranjeet.communicationschedulerservice.response.TaskResponseDto;
import com.ranjeet.communicationschedulerservice.respository.TaskDetailsRepository;
import com.ranjeet.communicationschedulerservice.service.JobDetailsService;
import com.ranjeet.communicationschedulerservice.service.TaskDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


@Component
@Slf4j
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
    public List<TaskResponseDto> transform(List<TaskDetails> taskDetails) {
        List<TaskResponseDto> taskResponseDtoList = new ArrayList<>();
        taskDetails.stream()
                .filter(Objects::nonNull)
                .forEach(taskDetail -> taskResponseDtoList.add(this.transform(taskDetail)));
        return taskResponseDtoList;
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
    @Transactional
    public TaskDetails getTaskDetails(Integer id) {
        return taskDetailsRepository.findById(id).orElse(null);
    }

    @Override
    public List<TaskDetails> getTask(Integer taskId) {
        if(Objects.isNull(taskId)){
            return taskDetailsRepository.findAll();
        }
        return Collections.singletonList(getTaskDetails(taskId));
    }

    @Override
    @Transactional
    public void deleteTask(Integer taskId) {
        TaskDetails taskDetails = taskDetailsRepository.getReferenceById(taskId);
        jobDetailsService.deleteJobDetailsByTaskId(taskDetails.getId());
        taskDetailsRepository.delete(taskDetails);
    }
}
