package com.bakabooth.user.domain.entity;

import com.bakabooth.user.domain.vo.UserVO;
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

    // 0 - 关店, 1 - 营业, 3 - 禁用
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

    public User(String phone, String password) {
        this.userStateCode = 0;
        this.shopStateCode = 0;
        this.phone = phone;
        this.password = password;
        this.introduction = "";
        this.announcement = "";
        this.followers = 0L;
        this.avatarFileId = 0L;
    }

    public static UserVO toUserVO(User user, Integer modeCode, String avatarUrl) {
        UserVO userVO = new UserVO();

        switch (modeCode) {
            case 2:
                userVO.setUserStateCode(user.userStateCode);
                userVO.setShopStateCode(user.shopStateCode);
                userVO.setPhone(user.getPhone());
                userVO.setCreatedAt(Instant.now());
            case 1:
                userVO.setIntroduction(user.getIntroduction());
                userVO.setAnnouncement(user.getAnnouncement());
            case 0:
                userVO.setId(user.getId());
                userVO.setUsername(user.getUsername());
                userVO.setFollowers(user.getFollowers());
                userVO.setAvatarUrl(avatarUrl);
                break;
            default:
                throw new RuntimeException("modeCode错误");
        }

        return userVO;
    }

}
