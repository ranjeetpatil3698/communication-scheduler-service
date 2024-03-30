CREATE TABLE `job_details` (
  `id` int NOT NULL AUTO_INCREMENT,
  `task_id` int DEFAULT NULL,
  `cron_expression` varchar(20) DEFAULT NULL,
  `next_run_epoch` bigint DEFAULT NULL,
  `job_status` enum('SUCCESS','FAILED','RUNNING') DEFAULT NULL,
  `retry_count` int DEFAULT NULL,
  `last_run_time` datetime DEFAULT NULL,
  `next_run_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_task_id` (`task_id`),
  CONSTRAINT `fk_task_id` FOREIGN KEY (`task_id`) REFERENCES `task_details` (`id`)
);
