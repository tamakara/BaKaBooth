package com.bakabooth.order.service.impl;

import com.bakabooth.order.domain.entity.Order;
import com.bakabooth.order.mapper.OrderMapper;
import com.bakabooth.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;

    @Override
    public Long createOrder(Long userId, Long itemId, Long quantity) {
        Order order = new Order(userId, itemId, quantity);



        orderMapper.insert(order);
        return 0L;
    }

}
