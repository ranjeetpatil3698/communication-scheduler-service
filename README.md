# Communication Scheduler Service

---
A scheduler service to send scheduled emails to desired email address.

#### Technologies Used
1. Java (**v17**)
2. Spring Boot 
3. Spring MVC 
4. Spring Data JPA 
5. MySQL (**v8.3.0**)
6. Flyway (**v17**)
7. Micrometer
8. SpringDoc
9. Maven (**v3.8.5**)
10. Git
---
#### Database Schema for `communication_scheduler_service`

`task_details` table

| Field                 | Type         |
|-----------------------|--------------|
| id                    | int          |
| sender                | varchar(255) |
| communication_address | varchar(255) |
| message               | text         |
| cron_expression       | varchar(20)  |

`job_details` table

| Field           | Type                               |
|-----------------|------------------------------------|
| id              | int                                |
| task_id         | int                                |
| cron_expression | varchar(20)                        |
| next_run_epoch  | bigint                             |
| job_status      | enum('SUCCESS','FAILED','RUNNING') |
| retry_count     | int                                |
| last_run_time   | datetime                           |
| next_run_time   | datetime                           |

---
#### REST ENDPOINTS

1. POST **/task**
```json
{
    "sender":"<FROM_NAME>",
    "communicationAddress":"<RECEIVERS_EMAIL_ADDRESS>",
    "message":"<REMINDER_EMAIL_HTML>",
    "cronExpression":"<CRON_EXPRESSION>"
}
```

2. GET **/task**
```json
[
  {
    "id": 1,
    "sender": "<FROM_NAME>",
    "communicationAddress": "<RECEIVERS_EMAIL_ADDRESS>",
    "message": "<REMINDER_EMAIL_HTML>",
    "cronExpression": "<CRON_EXPRESSION>"
  }
]
```

3. GET **/task?taskId=<TASK_ID>**
```json
[
  {
    "id": 1,
    "sender": "<FROM_NAME>",
    "communicationAddress": "<RECEIVERS_EMAIL_ADDRESS>",
    "message": "<REMINDER_EMAIL_HTML>",
    "cronExpression": "<CRON_EXPRESSION>"
  }
]
```

4. DELETE **/task?taskId=<TASK_ID>**

---