package com.ranjeet.communicationschedulerservice.controller;

import com.ranjeet.communicationschedulerservice.entity.TaskDetails;
import com.ranjeet.communicationschedulerservice.request.TaskRequestDto;
import com.ranjeet.communicationschedulerservice.response.TaskResponseDto;
import com.ranjeet.communicationschedulerservice.service.TaskDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TaskController {
    @Autowired
    TaskDetailsService taskDetailsService;
    @RequestMapping(value = "/task",method = RequestMethod.POST)
    TaskResponseDto addNewTask(@RequestBody TaskRequestDto taskRequestDto){
        TaskDetails taskDetailsRequest = taskDetailsService.transform(taskRequestDto);
        TaskDetails taskDetails = taskDetailsService.addTask(taskDetailsRequest);
        return taskDetailsService.transform(taskDetails);
    }

}
