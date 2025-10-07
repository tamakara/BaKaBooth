package com.bakabooth.order.service.impl;

import com.bakabooth.common.client.ItemClient;
import com.bakabooth.order.domain.entity.Order;
import com.bakabooth.order.domain.vo.OrderVO;
import com.bakabooth.order.mapper.OrderMapper;
import com.bakabooth.order.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    private final OrderMapper orderMapper;
    private final ItemClient itemClient;

    @Override
    @Transactional
    public Long createOrder(Long userId, Long itemId) {
        boolean sold = exists(new LambdaQueryWrapper<Order>()
                .eq(Order::getItemId, itemId)
                .ne(Order::getStateCode, 0)
        );
        if (sold) {
            throw new RuntimeException("商品已被购买");
        }

        itemClient.lockItem(itemId);

        Order order = new Order();
        order.setUserId(userId);
        order.setItemId(itemId);
        order.setStateCode(1);
        orderMapper.insert(order);

        return order.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderVO getOrderVO(Long userId, Long orderId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }
        OrderVO vo = new OrderVO();
        BeanUtils.copyProperties(order, vo);
        return vo;
    }

}