package com.bakabooth.user.domain.vo;

import lombok.Data;

@Data
public class ShopManagePageVO {
    private Long userId;
    private String name;
    private String description;
    private String state;
    private Long follower;
}
