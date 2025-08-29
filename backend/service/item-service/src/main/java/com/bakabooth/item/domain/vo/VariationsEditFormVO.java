package com.bakabooth.item.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class VariationsEditFormVO {
    private Long id;
    private String name;
    private Double price;
    private Long stock;
    private List<Long> files;
}
