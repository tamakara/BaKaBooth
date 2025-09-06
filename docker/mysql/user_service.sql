CREATE DATABASE IF NOT EXISTS user_service_db;
CREATE USER IF NOT EXISTS 'user_service_user'@'%' IDENTIFIED BY '0d000721';
GRANT ALL PRIVILEGES ON user_service_db.* TO 'user_service_user'@'%';
FLUSH PRIVILEGES;

USE user_service_db;

CREATE TABLE `user`
(
    `id`             BIGINT       NOT NULL AUTO_INCREMENT,
    `phone`          CHAR(11)     NOT NULL,
    `email`          VARCHAR(255) DEFAULT NULL,
    `username`       VARCHAR(255) DEFAULT NULL,
    `password`       VARCHAR(255) NOT NULL,
    `nickname`       VARCHAR(255) NOT NULL,
    `avatar_file_id` BIGINT       NOT NULL,
    `created_at`     TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    `updated_at`     TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_username` (`phone`),
    UNIQUE KEY `idx_email` (`email`)
) DEFAULT CHARSET = utf8mb4;

CREATE TABLE `shop`
(
    `id`          BIGINT       NOT NULL AUTO_INCREMENT,
    `user_id`     BIGINT       NOT NULL,
    `state`       VARCHAR(255) NOT NULL,
    `name`        VARCHAR(255) NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    `followers`    BIGINT DEFAULT 0,
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_user_id` (`user_id`)
) DEFAULT CHARSET = utf8mb4;
