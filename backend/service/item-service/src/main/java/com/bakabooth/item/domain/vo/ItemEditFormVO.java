package com.bakabooth.item.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class ItemEditFormVO {
    private Integer stateCode;

    private String name;
    private Double price;
    private String description;
    private Integer postage;
    private Integer returnPeriod;
    private Integer deliveryPeriod;

    private List<Long> images;
    private List<String> tags;
}
