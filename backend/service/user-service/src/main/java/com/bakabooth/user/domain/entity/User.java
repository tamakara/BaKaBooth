package com.bakabooth.user.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.Instant;

@Data
@TableName("user")
public class User {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "phone")
    private String phone;

    @TableField(value = "username")
    private String username;

    @TableField(value = "password")
    private String password;

    @TableField(value = "email")
    private String email;

    @TableField(value = "state")
    private String state;

    @TableField(value = "description")
    private String description;

    @TableField(value = "followers")
    private Long followers;

    @TableField(value = "avatar_file_id")
    private Long avatarFileId;

    @TableField(value = "created_at")
    private Instant createdAt;

    @TableField(value = "updated_at")
    private Instant updatedAt;

    public User() {
        this.state = "closed";
        this.description = "";
        this.followers = 0L;
        this.avatarFileId = 0L;
    }

}
