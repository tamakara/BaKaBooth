USE user_service_db;

CREATE TABLE `user`
(
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username`    VARCHAR(255) NOT NULL COMMENT '用户名',
    `password`    VARCHAR(255) NOT NULL COMMENT '密码',
    `nickname`    VARCHAR(255) DEFAULT NULL COMMENT '昵称',
    `email`       VARCHAR(255) DEFAULT NULL COMMENT '电子邮件',
    `avatar_file` VARCHAR(255) DEFAULT NULL COMMENT '头像文件路径',
    `created_at`  BIGINT       NOT NULL COMMENT '创建时间戳',
    `updated_at`  BIGINT       NOT NULL COMMENT '更新时间戳',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_username` (`username`),
    KEY `idx_email` (`email`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户表';

INSERT INTO `user` (`username`, `password`, `nickname`, `email`, `avatar_file`, `created_at`, `updated_at`)
VALUES ('john_doe', 'password123', 'John Doe', 'john.doe@example.com', '/avatars/john_doe.png', UNIX_TIMESTAMP(),
        UNIX_TIMESTAMP()),
       ('jane_smith', 'password456', 'Jane Smith', 'jane.smith@example.com', '/avatars/jane_smith.png',
        UNIX_TIMESTAMP(), UNIX_TIMESTAMP()),
       ('bob_jones', 'password789', 'Bob Jones', 'bob.jones@example.com', '/avatars/bob_jones.png', UNIX_TIMESTAMP(),
        UNIX_TIMESTAMP()),
       ('alice_williams', 'password012', 'Alice Williams', 'alice.williams@example.com', '/avatars/alice_williams.png',
        UNIX_TIMESTAMP(), UNIX_TIMESTAMP());