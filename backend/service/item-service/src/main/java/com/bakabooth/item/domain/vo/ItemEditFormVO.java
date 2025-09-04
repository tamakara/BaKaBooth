package com.bakabooth.item.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class ItemEditFormVO {
    private String state;
    private String name;
    private String description;
    private List<Long> images;
    private List<String> tags;
    private List<VariationEditFormVO> variations;
}
