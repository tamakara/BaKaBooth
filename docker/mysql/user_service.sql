CREATE DATABASE IF NOT EXISTS user_service_db;
CREATE USER IF NOT EXISTS 'user_service_user'@'%' IDENTIFIED BY '0d000721';
GRANT ALL PRIVILEGES ON user_service_db.* TO 'user_service_user'@'%';
FLUSH PRIVILEGES;

USE user_service_db;
CREATE TABLE `user`
(
    `id`          BIGINT       NOT NULL AUTO_INCREMENT,
    `shop_id`     BIGINT       DEFAULT NULL,
    `phone`       CHAR(11)     NOT NULL,
    `email`       VARCHAR(255) DEFAULT NULL,
    `password`    VARCHAR(255) NOT NULL,
    `nickname`    VARCHAR(255) NOT NULL,
    `avatar_file` VARCHAR(255) NOT NULL,
    `created_at`  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    `updated_at`  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_shop_id` (`shop_id`),
    UNIQUE KEY `idx_username` (`phone`),
    UNIQUE KEY `idx_email` (`email`)
) DEFAULT CHARSET = utf8mb4;

INSERT INTO `user` (`shop_id`, `phone`, `email`, `password`, `nickname`, `avatar_file`)
VALUES (1,
        11111111111,
        'test@test.test',
        'testtest',
        'test',
        'test');