package com.ranjeet.communicationschedulerservice.controller;

import com.ranjeet.communicationschedulerservice.Exception.InvalidCronExpressionException;
import com.ranjeet.communicationschedulerservice.Exception.TaskDetailsNotFoundException;
import com.ranjeet.communicationschedulerservice.entity.TaskDetails;
import com.ranjeet.communicationschedulerservice.request.TaskRequestDto;
import com.ranjeet.communicationschedulerservice.response.TaskResponseDto;
import com.ranjeet.communicationschedulerservice.service.TaskDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {
    @Autowired
    TaskDetailsService taskDetailsService;

    @Operation(summary = "Create A Task", description = "Create A Task To Schedule an email to be sent at a specified time")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task Created"),
            @ApiResponse(responseCode = "400", description = "Invalid Cron Expression")
    })
    @RequestMapping(value = "/task",method = RequestMethod.POST)
    TaskResponseDto addNewTask(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Request Body") @RequestBody TaskRequestDto taskRequestDto) throws InvalidCronExpressionException {
        TaskDetails taskDetailsRequest = taskDetailsService.transform(taskRequestDto);
        TaskDetails taskDetails = taskDetailsService.addTask(taskDetailsRequest);
        return taskDetailsService.transform(taskDetails);
    }

    @Operation(summary = "Get A Task", description = "Get Details Of Scheduled Task")
    @RequestMapping(value = "/task",method = RequestMethod.GET)
    List<TaskResponseDto> getTask(@Parameter(description ="Get Task by ID If ID Is Provided, Otherwise Return All Tasks ") @RequestParam(required = false) Integer taskId){
        List<TaskDetails> taskDetailsList = taskDetailsService.getTask(taskId);
        return taskDetailsService.transform(taskDetailsList);
    }

    @Operation(summary = "Delete A Task", description = "Delete The Task By Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task Deleted"),
            @ApiResponse(responseCode = "400", description = "Task Details Not Found")
    })
    @RequestMapping(value = "/task",method = RequestMethod.DELETE)
    void deleteTask(@Parameter(description ="Delete The Task By Id") @RequestParam Integer taskId) throws TaskDetailsNotFoundException {
        taskDetailsService.deleteTask(taskId);
    }

}
