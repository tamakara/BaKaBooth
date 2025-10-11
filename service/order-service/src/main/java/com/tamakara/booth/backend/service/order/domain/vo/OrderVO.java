package com.tamakara.booth.backend.service.order.domain.vo;

import com.tamakara.booth.backend.service.order.domain.pojo.OrderState;
import lombok.Data;

import java.time.Instant;

@Data
public class OrderVO {
    private Long id;
    private Long itemId;
    private Long sellerId;
    private OrderState orderState;

    private Double payAmount;

    private Instant createdAt;
    private Instant payAt;
    private Instant shippedAt;
    private Instant completedAt;

    private Instant refundRequestAt;
    private Instant refundAt;

    private Instant cancelledAt;
}
