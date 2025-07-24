package com.bakabooth.user.domain.vo;

import lombok.Data;

@Data
public class UserStatusVO {
    private Long id;
    private String token;
    private String username;
    private String nickname;
    private String avatarFile;
}
