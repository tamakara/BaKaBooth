package com.bakabooth.common.domain.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class UserDTO {
    private Long id;
    private Long shopId;
    private Integer phone;
    private String email;
    private String password;
    private String nickname;
    private String avatarFile;
    private Instant createdAt;
    private Instant updatedAt;
}
