CREATE TABLE `task_details` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sender` varchar(255) DEFAULT NULL,
  `communication_address` varchar(255) DEFAULT NULL,
  `message` text,
  `cron_expression` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
