package com.bakabooth.item.domain.vo;

import lombok.Data;

@Data
public class ItemQueryFormVO {
    private Long sellerId = 0L;
    private Integer stateCode = 0;
    private Integer pageNo = 1;
    private Integer pageSize = 20;
}
