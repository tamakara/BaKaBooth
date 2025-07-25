package com.bakabooth.user.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLoginVO {
    private String token;
    private Boolean isLogin;
    private String role;
}
