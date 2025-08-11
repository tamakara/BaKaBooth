package com.bakabooth.item.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class ItemEditFormVO {
    private Long id;
    private Long shopId;
    private String state;
    private String name;
    private String description;
    private List<String> images;
    private List<String> tags;
    private List<VariationsEditFormVO> variations;
}
