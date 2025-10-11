package com.tamakara.booth.backend.service.order.domain.pojo;

public enum OrderState {
    CREATED,
    PAID,
    SHIPPED,
    COMPLETED,
    REFUND_REQUESTED,
    REFUNDED,
    CANCELLED
}
