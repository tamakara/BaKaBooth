package com.bakabooth.order.domain.vo;

import lombok.Data;

@Data
public class OrderEditFormVO {
    private Long variationId;
    private String price;
    private String quantity;
}
