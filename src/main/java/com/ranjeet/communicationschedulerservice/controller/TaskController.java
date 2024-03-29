package com.ranjeet.communicationschedulerservice.controller;

import com.ranjeet.communicationschedulerservice.entity.TaskDetails;
import com.ranjeet.communicationschedulerservice.request.TaskRequestDto;
import com.ranjeet.communicationschedulerservice.response.TaskResponseDto;
import com.ranjeet.communicationschedulerservice.service.TaskDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @RequestMapping(value = "/task",method = RequestMethod.GET)
    List<TaskResponseDto> getTask(@RequestParam(required = false) Integer taskId){
        List<TaskDetails> taskDetailsList = taskDetailsService.getTask(taskId);
        return taskDetailsService.transform(taskDetailsList);
    }

    @RequestMapping(value = "/task",method = RequestMethod.DELETE)
    void deleteTask(@RequestParam Integer taskId){
        taskDetailsService.deleteTask(taskId);
    }

}
