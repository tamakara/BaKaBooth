package com.bakabooth.user.domain.dto;

import lombok.Data;

@Data
public class RegisterFormDTO {
    private String phone;
    private String nickname;
    private String username;
    private String password;
}
