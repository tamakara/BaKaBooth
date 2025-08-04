USE item_service_db;

CREATE TABLE `item`
(
    `id`         BIGINT       NOT NULL AUTO_INCREMENT,
    `name`       VARCHAR(255) NOT NULL,
    `state`      VARCHAR(255) NOT NULL,
    `favorite`   BIGINT       NOT NULL,
    `user_id`    BIGINT       NOT NULL,
    `cover_file` VARCHAR(255) DEFAULT NULL,
    `variations` JSON         NOT NULL,
    PRIMARY KEY (`id`)
) DEFAULT CHARSET = utf8mb4;