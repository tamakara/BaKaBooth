package com.bakabooth.order.domain.vo;

import lombok.Data;

import java.time.Instant;

@Data
public class OrderVO {
    private Long id;
    private Long userId;
    private Long itemId;
    private Integer stateCode;

    private Double payAmount;

    private Instant createdAt;
    private Instant payAt;
    private Instant deliveryAt;
    private Instant receiveAt;
    private Instant finishAt;
    private Instant returnRequestAt;
    private Instant returnAt;
    private Instant refundAt;
    private Instant returnFinishAt;
}
