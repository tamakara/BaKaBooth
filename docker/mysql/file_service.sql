CREATE DATABASE IF NOT EXISTS file_service_db;
CREATE USER IF NOT EXISTS 'file_service_user'@'%' IDENTIFIED BY '0d000721';
GRANT ALL PRIVILEGES ON file_service_db.* TO 'file_service_user'@'%';
FLUSH PRIVILEGES;

USE file_service_db;
CREATE TABLE `file`
(
    `id`        BIGINT       NOT NULL AUTO_INCREMENT,
    `name`      VARCHAR(255) NOT NULL,
    `hash`      VARCHAR(255) NOT NULL,
    `size`      BIGINT       NOT NULL,
    `created_at` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) DEFAULT CHARSET = utf8mb4;
