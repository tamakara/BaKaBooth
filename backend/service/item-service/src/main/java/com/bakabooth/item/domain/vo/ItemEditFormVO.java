package com.bakabooth.item.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class ItemEditFormVO {
    private Integer stateCode;
    private String name;
    private String description;
    private String deliveryTime;
    private String shippingDetails;
    private String returnPolicy;
    private String warrantyPeriod;
    private List<Long> images;
    private List<String> tags;
    private List<VariationEditFormVO> variations;
}
