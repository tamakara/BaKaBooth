package com.bakabooth.order.service;

public interface OrderService {
    public Long createOrder(Long userId, Long itemId, Long quantity);

}
