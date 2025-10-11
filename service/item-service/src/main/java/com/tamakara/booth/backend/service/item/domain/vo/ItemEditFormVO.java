package com.tamakara.booth.backend.service.item.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class ItemEditFormVO {
    private Integer stateCode;
    private Integer deliveryMethodCode;

    private Double price;
    private Double postage;

    private String name;
    private String description;

    private List<Long> images;
}
