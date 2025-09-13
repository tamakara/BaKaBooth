package com.bakabooth.order.service;

public interface OrderService {
    Long createOrder(Long userId, Long itemId, Long quantity);

}
