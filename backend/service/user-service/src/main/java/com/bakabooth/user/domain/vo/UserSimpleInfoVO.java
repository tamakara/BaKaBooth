package com.bakabooth.user.domain.vo;

import lombok.Data;

@Data
public class UserSimpleInfoVO {
    private Long id;
    private String nickname;
    private String avatarUrl;
}
