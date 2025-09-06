package com.bakabooth.item.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class ItemVO {
    private Long userId;
    private String name;
    private String description;
    private Long favorites;
    private List<String> imageUrls;
    private List<String> tags;
    private List<VariationVO> variations;
}
