package com.bakabooth.item.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class VariationVO {
    private String name;
    private Double type;
    private Double price;
    private Long stock;
}
