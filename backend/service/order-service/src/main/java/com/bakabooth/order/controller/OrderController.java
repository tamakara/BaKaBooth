package com.bakabooth.order.controller;

import com.bakabooth.order.domain.vo.OrderVO;
import com.bakabooth.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "订单接口")
@RestController
@RequiredArgsConstructor
public class OrderController {
    public final OrderService orderService;

    @Operation(summary = "创建订单")
    @PostMapping("/create/{itemId}")
    public ResponseEntity<Long> createOrder(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable("itemId") Long itemId
    ) {
        Long orderId = orderService.createOrder(userId, itemId);
        return ResponseEntity.ok(orderId);
    }

    @Operation(summary = "获取订单信息")
    @GetMapping("/vo/order/{orderId}")
    public ResponseEntity<OrderVO> getOrderVO(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable("orderId") Long orderId
    ) {
        OrderVO vo = orderService.getOrderVO(userId, orderId);
        return ResponseEntity.ok(vo);
    }
}
