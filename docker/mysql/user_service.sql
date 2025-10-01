CREATE DATABASE IF NOT EXISTS user_service_db;
CREATE USER IF NOT EXISTS 'user_service_user'@'%' IDENTIFIED BY '0d000721';
GRANT ALL PRIVILEGES ON user_service_db.* TO 'user_service_user'@'%';
FLUSH PRIVILEGES;

USE user_service_db;
CREATE TABLE `user`
(
    `id`              BIGINT       NOT NULL AUTO_INCREMENT,
    `user_state_code` INTEGER      NOT NULL,
    `shop_state_code` INTEGER      NOT NULL,
    `phone`           CHAR(11)     NOT NULL,
    `password`        VARCHAR(255) NOT NULL,
    `username`        VARCHAR(255) NOT NULL,
    `introduction`    VARCHAR(255) NOT NULL,
    `announcement`    VARCHAR(255) NOT NULL,
    `followers`       BIGINT       NOT NULL,
    `avatar_file_id`  BIGINT       NOT NULL,
    `created_at`      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    `updated_at`      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_username` (`username`),
    UNIQUE KEY `idx_phone` (`phone`)
) DEFAULT CHARSET = utf8mb4;

CREATE TABLE `favorite`
(
    `id`      BIGINT NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL,
    `item_id` BIGINT NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY (`user_id`, `item_id`)
) DEFAULT CHARSET = utf8mb4;
