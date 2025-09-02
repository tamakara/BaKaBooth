CREATE DATABASE IF NOT EXISTS shop_service_db;
CREATE USER IF NOT EXISTS 'shop_service_user'@'%' IDENTIFIED BY '0d000721';
GRANT ALL PRIVILEGES ON shop_service_db.* TO 'shop_service_user'@'%';
FLUSH PRIVILEGES;

USE shop_service_db;
CREATE TABLE `shop`
(
    `id`          BIGINT       NOT NULL AUTO_INCREMENT,
    `user_id`     BIGINT       NOT NULL,
    `state`       VARCHAR(255) NOT NULL,
    `name`        VARCHAR(255) NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    `follower`    BIGINT DEFAULT 0,
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_user_id` (`user_id`)
) DEFAULT CHARSET = utf8mb4;
INSERT INTO `shop` (`user_id`, `state`, `name`, `description`)
VALUES (1,
        'private',
        'test',
        'test');