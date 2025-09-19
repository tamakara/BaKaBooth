CREATE DATABASE IF NOT EXISTS order_service_db;
CREATE USER IF NOT EXISTS 'order_service_user'@'%' IDENTIFIED BY '0d000721';
GRANT ALL PRIVILEGES ON order_service_db.* TO 'order_service_user'@'%';
FLUSH PRIVILEGES;

USE order_service_db;
CREATE TABLE `order`
(
    `id`             BIGINT       NOT NULL AUTO_INCREMENT,
    `user_id`        BIGINT       NOT NULL,
    `item_id`        BIGINT       NOT NULL,
    `state`          VARCHAR(255) NOT NULL,
    `payment_method` VARCHAR(255) NOT NULL,
    `payment_amount` DOUBLE       NOT NULL,
    `created_at`     TIMESTAMP    NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY (`user_id`, `item_id`)
) DEFAULT CHARSET = utf8mb4;