package com.bakabooth.user.domain.vo;

import lombok.Data;

import java.time.Instant;

@Data
public class UserVO {
    private Long id;
    private Integer userStateCode;
    private Integer shopStateCode;
    private String phone;
    private String username;
    private String introduction;
    private String announcement;
    private Long followers;
    private String avatarUrl;
    private Instant createdAt;
}
