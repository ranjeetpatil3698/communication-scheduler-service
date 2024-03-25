package com.ranjeet.communicationschedulerservice.respository;

import com.ranjeet.communicationschedulerservice.entity.TaskDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface TaskDetailsRepository extends JpaRepository<TaskDetails,Integer> {

}
