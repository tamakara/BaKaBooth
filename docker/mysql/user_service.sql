USE user_service_db;

CREATE TABLE `user`
(
    `id`          BIGINT       NOT NULL AUTO_INCREMENT,
    `phone`       INTEGER      DEFAULT NULL,
    `email`       VARCHAR(255) DEFAULT NULL,
    `password`    VARCHAR(255) NOT NULL,
    `nickname`    VARCHAR(255) DEFAULT NULL,
    `avatar_file` VARCHAR(255) DEFAULT NULL,
    `created_at`  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    `updated_at`  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_username` (`phone`),
    UNIQUE KEY `idx_email` (`email`)
) DEFAULT CHARSET = utf8mb4;

INSERT INTO `user` (`phone`, `email`, `password`, `nickname`, `avatar_file`)
VALUES (666,
        'test@test.test',
        'test',
        'test',
        'test');