package com.bakabooth.item.domain.vo;

import com.bakabooth.item.domain.pojo.Variation;
import lombok.Data;

import java.util.List;

@Data
public class ItemManageVO {
    private Long id;
    private String name;
    private String state;
    private Long favorite;
    private String coverUrl;
    private List<Variation> variations;
}
