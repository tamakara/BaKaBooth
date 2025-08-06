CREATE DATABASE IF NOT EXISTS item_service_db;
CREATE USER IF NOT EXISTS 'item_service_user'@'%' IDENTIFIED BY '0d000721';
GRANT ALL PRIVILEGES ON item_service_db.* TO 'item_service_user'@'%';
FLUSH PRIVILEGES;

USE item_service_db;
CREATE TABLE `item`
(
    `id`         BIGINT       NOT NULL AUTO_INCREMENT,
    `name`       VARCHAR(255) NOT NULL,
    `state`      VARCHAR(255) NOT NULL,
    `favorite`   BIGINT       NOT NULL,
    `shop_id`    BIGINT       NOT NULL,
    `cover_file` VARCHAR(255) DEFAULT NULL,
    `variations` JSON         NOT NULL,
    `created_at` TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) DEFAULT CHARSET = utf8mb4;