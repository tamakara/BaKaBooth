package com.bakabooth.user.domain.vo;

import lombok.Data;

@Data
public class UserLoginVO {
    private String token;
    private UserInfoVO userInfoVO;
}
