CREATE DATABASE IF NOT EXISTS file_service_db;
CREATE USER IF NOT EXISTS 'file_service_user'@'%' IDENTIFIED BY '0d000721';
GRANT ALL PRIVILEGES ON file_service_db.* TO 'file_service_user'@'%';
FLUSH PRIVILEGES;

USE file_service_db;

CREATE TABLE `file`
(
    `id`        BIGINT       NOT NULL AUTO_INCREMENT,
    `user_id`   BIGINT       NOT NULL,
    `name`      VARCHAR(255) NOT NULL,
    `hash`      VARCHAR(255) NOT NULL,
    `size`      BIGINT       NOT NULL,
    `is_public` BOOLEAN      NOT NULL,
    PRIMARY KEY (`id`)
) DEFAULT CHARSET = utf8mb4;

CREATE TABLE `permission`
(
    `id`           BIGINT  NOT NULL AUTO_INCREMENT,
    `file_id`      BIGINT  NOT NULL,
    `user_id`      BIGINT  NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY (`file_id`, `user_id`)
) DEFAULT CHARSET = utf8mb4;

