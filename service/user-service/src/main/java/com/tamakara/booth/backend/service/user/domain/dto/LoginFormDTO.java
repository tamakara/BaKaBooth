package com.tamakara.booth.backend.service.user.domain.dto;

import lombok.Data;

@Data
public class LoginFormDTO {
    private String phone;
    private String password;
}
