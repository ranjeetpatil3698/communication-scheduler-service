package com.ranjeet.communicationschedulerservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * TODO:
 *  1. Fix URL scheme BUG. DONE
 *  2. Add endpoint to delete the task and job DONE
 *  3. Endpoint to get All the task DONE
 *  4. Fix RUNNING bug , can't reproduce
 *  5. Expose Swagger documentation DONE
 *  6. Implement Mailgun -------
 *  7. Add html support to message DONE
 *  8. handle graceful shutdown and try a @Prototype and Callable approach. Didn't work
 * */

@SpringBootApplication
@Slf4j
public class CommunicationSchedulerServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(CommunicationSchedulerServiceApplication.class, args);
	}
}


