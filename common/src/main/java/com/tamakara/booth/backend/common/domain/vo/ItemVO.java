package com.tamakara.booth.backend.common.domain.vo;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class ItemVO {
    private Long id;
    private Long userId;
    private Boolean isSeller;
    private Integer stateCode;
    private Integer deliveryMethodCode;

    private Double price;
    private Double postage;

    private String name;
    private String description;

    private Long favorites;

    private Instant createdAt;
    private Instant updatedAt;

    private List<String> images;
}
