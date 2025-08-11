CREATE DATABASE IF NOT EXISTS item_service_db;
CREATE USER IF NOT EXISTS 'item_service_user'@'%' IDENTIFIED BY '0d000721';
GRANT ALL PRIVILEGES ON item_service_db.* TO 'item_service_user'@'%';
FLUSH PRIVILEGES;

USE item_service_db;
CREATE TABLE `item`
(
    `id`          BIGINT       NOT NULL AUTO_INCREMENT,
    `shop_id`     BIGINT       NOT NULL,
    `state`       VARCHAR(255) NOT NULL,
    `name`        VARCHAR(255) NOT NULL,
    `description` TEXT         NOT NULL,
    `favorite`    BIGINT       NOT NULL,
    PRIMARY KEY (`id`)
) DEFAULT CHARSET = utf8mb4;

CREATE TABLE `image`
(
    `id`          BIGINT       NOT NULL AUTO_INCREMENT,
    `item_id`     BIGINT       NOT NULL,
    `order_index` INTEGER      NOT NULL,
    `hash`        VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY (`item_id`, `order_index`)
) DEFAULT CHARSET = utf8mb4;

CREATE TABLE `tag`
(
    `id`      BIGINT       NOT NULL AUTO_INCREMENT,
    `item_id` BIGINT       NOT NULL,
    `name`    VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY (`item_id`, `name`)
) DEFAULT CHARSET = utf8mb4;

CREATE TABLE `variation`
(
    `id`          BIGINT       NOT NULL AUTO_INCREMENT,
    `item_id`     BIGINT       NOT NULL,
    `order_index` INTEGER      NOT NULL,
    `name`        VARCHAR(255) NOT NULL,
    `price`       DOUBLE       NOT NULL,
    `stock`       BIGINT       NOT NULL,
    `sales`       BIGINT       NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY (`item_id`, `order_index`)
) DEFAULT CHARSET = utf8mb4;

CREATE TABLE `file`
(
    `id`           BIGINT       NOT NULL AUTO_INCREMENT,
    `variation_id` BIGINT       NOT NULL,
    `order_index`  INTEGER      NOT NULL,
    `hash`         VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY (`variation_id`, `order_index`)
) DEFAULT CHARSET = utf8mb4;