package com.bakabooth.user.domain.vo;

import lombok.Data;

import java.time.Instant;

@Data
public class UserVO {
    private Long id;
    private Boolean isCurrentUser;
    private Boolean isOnline;
    private Boolean isOpen;

    private String username;
    private String introduction;
    private String announcement;
    private Long followers;
    private String avatarUrl;

    private String phone;
    private Double balance;
    private Instant createdAt;
}
