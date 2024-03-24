CREATE TABLE IF NOT EXISTS task_details (
    id INT AUTO_INCREMENT PRIMARY KEY,
    sender VARCHAR(255),
    communicationAddress VARCHAR(255),
    message VARCHAR(255)
);