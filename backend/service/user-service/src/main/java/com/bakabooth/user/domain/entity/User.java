package com.bakabooth.user.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.Instant;

@Data
@TableName("user")
public class User {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    // 0 - 下线, 1 - 在线 ,2 - 已注销, 3 - 禁用
    @TableField(value = "user_state_code")
    private Integer userStateCode;

    // 0 - 关店, 1 - 营业, 2 - 禁用
    @TableField(value = "shop_state_code")
    private Integer shopStateCode;

    @TableField(value = "phone")
    private String phone;

    @TableField(value = "password")
    private String password;

    @TableField(value = "username")
    private String username;

    @TableField(value = "introduction")
    private String introduction;

    @TableField(value = "announcement")
    private String announcement;

    @TableField(value = "followers")
    private Long followers;

    @TableField(value = "avatar_file_id")
    private Long avatarFileId;

    @TableField(value = "created_at")
    private Instant createdAt;

    @TableField(value = "updated_at")
    private Instant updatedAt;

    public User() {
        this.phone = "";
        this.password = "";
        this.userStateCode = 0;
        this.shopStateCode = 0;
        this.username = "";
        this.introduction = "";
        this.announcement = "";
        this.followers = 0L;
        this.avatarFileId = 1L;
    }
}
