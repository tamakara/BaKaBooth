package com.bakabooth.item.domain.vo;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class ItemVO {
    private Long id;
    private Long userId;
    private Boolean isSeller;
    private Integer stateCode;

    private String name;
    private Double price;
    private String description;
    private Integer postage;
    private Integer returnPeriod;
    private Integer deliveryPeriod;

    private Long favorites;

    private Instant createdAt;
    private Instant updatedAt;

    private List<String> images;
    private List<String> tags;
}
