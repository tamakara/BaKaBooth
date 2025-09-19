package com.bakabooth.order.service;

import com.bakabooth.order.domain.entity.Order;
import com.bakabooth.order.domain.vo.OrderEditFormVO;
import com.bakabooth.order.domain.vo.OrderVO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OrderService extends IService<Order> {
    Long createOrder(Long userId, OrderEditFormVO orderEditFormVO);

    OrderVO getOrderVO(Long userId, Long orderId);
}
