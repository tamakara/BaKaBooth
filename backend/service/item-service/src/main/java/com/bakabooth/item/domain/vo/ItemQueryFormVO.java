package com.bakabooth.item.domain.vo;

import com.bakabooth.item.domain.pojo.ItemState;
import lombok.Data;

@Data
public class ItemQueryFormVO {
    private Long sellerId = 0L;
    private ItemState itemState = ItemState.ALL;
    private Integer pageNo = 1;
    private Integer pageSize = 20;
}
