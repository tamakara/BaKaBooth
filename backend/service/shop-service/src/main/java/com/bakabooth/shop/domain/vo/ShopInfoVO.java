package com.bakabooth.shop.domain.vo;

import lombok.Data;

@Data
public class ShopInfoVO {
    private Long shopId;
    private String name;
    private String description;
    private Long follower;
    private String state;
}
