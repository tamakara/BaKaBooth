package com.bakabooth.user.domain.vo;

import lombok.Data;

@Data
public class SellerVO {
    private String username;
    private String state;
    private String description;
    private Long followers;
    private String avatarUrl;
}
