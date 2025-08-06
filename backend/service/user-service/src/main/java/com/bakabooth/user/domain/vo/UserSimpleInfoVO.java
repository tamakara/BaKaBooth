package com.bakabooth.user.domain.vo;

import lombok.Data;

@Data
public class UserSimpleInfoVO {
    private Long userId;
    private String nickname;
    private String avatarUrl;
}
