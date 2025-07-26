package com.bakabooth.user.domain.vo;

import lombok.Data;

@Data
public class UserInfoVO {
    private String username;
    private String nickname;
    private String email;
    private String avatarFile;
}
