package com.bakabooth.order.controller;

import com.bakabooth.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "订单接口")
@RestController
@RequiredArgsConstructor
public class OrderController {
    public final OrderService orderService;

    @Operation(summary = "创建订单")
    @PostMapping("/create")
    public ResponseEntity<Long> createOrder(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestParam("itemId") Long itemId,
            @RequestParam("quantity") Long quantity
    ) {
        Long orderId = orderService.createOrder(userId, itemId, quantity);
        return ResponseEntity.ok(orderId);
    }

}
