CREATE TABLE IF NOT EXISTS job_details (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    task_id INTEGER,
    cron_expression VARCHAR(255),
    next_run_epoch INTEGER,
    job_status ENUM('SUCCESS','FAILED','RUNNING'),
    retry_count INTEGER,
    CONSTRAINT fk_task_id FOREIGN KEY (task_id) REFERENCES task_details(id)
);
