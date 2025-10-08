CREATE DATABASE IF NOT EXISTS order_service_db;
CREATE USER IF NOT EXISTS 'order_service_user'@'%' IDENTIFIED BY '0d000721';
GRANT ALL PRIVILEGES ON order_service_db.* TO 'order_service_user'@'%';
FLUSH PRIVILEGES;

USE order_service_db;
CREATE TABLE `order`
(
    `id`                BIGINT  NOT NULL AUTO_INCREMENT,
    `user_id`           BIGINT  NOT NULL,
    `item_id`           BIGINT  NOT NULL,
    `state_code`        INTEGER NOT NULL,
    `pay_amount`        DOUBLE    DEFAULT NULL,
    `created_at`        TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `pay_at`            TIMESTAMP DEFAULT NULL,
    `delivery_at`       TIMESTAMP DEFAULT NULL,
    `receive_at`        TIMESTAMP DEFAULT NULL,
    `finish_at`         TIMESTAMP DEFAULT NULL,
    `return_request_at` TIMESTAMP DEFAULT NULL,
    `return_at`         TIMESTAMP DEFAULT NULL,
    `refund_at`         TIMESTAMP DEFAULT NULL,
    `return_finish_at`  TIMESTAMP DEFAULT NULL,
    PRIMARY KEY (`id`)
) DEFAULT CHARSET = utf8mb4;