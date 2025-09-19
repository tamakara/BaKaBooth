package com.bakabooth.order.service.impl;

import com.bakabooth.common.client.ItemClient;
import com.bakabooth.order.domain.entity.Order;
import com.bakabooth.order.domain.vo.OrderEditFormVO;
import com.bakabooth.order.domain.vo.OrderVO;
import com.bakabooth.order.mapper.OrderMapper;
import com.bakabooth.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    private final ItemClient itemClient;

    @Override
    public Long createOrder(Long userId, OrderEditFormVO orderEditFormVO) {
        Order order = new Order(userId,orderEditFormVO);
        baseMapper.insert(order);
        return order.getId();
    }

    @Override
    public OrderVO getOrderVO(Long userId, Long orderId) {



        return null;
    }



}