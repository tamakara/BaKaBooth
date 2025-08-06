package com.bakabooth.user.domain.entity;

import com.bakabooth.common.domain.dto.UserDTO;
import com.bakabooth.user.domain.vo.UserSimpleInfoVO;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.Instant;

@Data
@TableName("user")
public class User {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "shop_id")
    private Long shopId;

    @TableField(value = "phone")
    private Integer phone;

    @TableField(value = "email")
    private String email;

    @TableField(value = "password")
    private String password;

    @TableField(value = "nickname")
    private String nickname;

    @TableField(value = "avatar_file")
    private String avatarFile;

    @TableField(value = "created_at")
    private Instant createdAt;

    @TableField(value = "updated_at")
    private Instant updatedAt;

    public UserSimpleInfoVO toUserSimpleInfoVO() {
        UserSimpleInfoVO vo = new UserSimpleInfoVO();
        vo.setUserId(this.id);
        vo.setNickname(this.nickname);
        vo.setAvatarUrl(this.avatarFile);
        return vo;
    }

    public UserDTO toUserDTO() {
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(this, dto);
        return dto;
    }
}
