package com.bakabooth.user.domain.vo;

import lombok.Data;

@Data
public class ShopVO {
    private Long userId;
    private String name;
    private String state;
    private String description;
    private Long followers;
    private String avatarUrl;
}
