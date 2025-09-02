package com.bakabooth.shop.controller;

import com.bakabooth.shop.domain.vo.ShopInfoVO;
import com.bakabooth.shop.service.ShopService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ShopController {
    private final ShopService shopService;

    @Operation(summary = "创建店铺")
    @PostMapping("/shop/create")
    public ResponseEntity<Long> create(@RequestBody Long userId) {
        Long shopId = shopService.create(userId);
        return ResponseEntity.ok(shopId);
    }

    @Operation(summary = "获取店铺信息")
    @GetMapping("/shop/info")
    public ResponseEntity<ShopInfoVO> getShopInfo(@RequestHeader("X-SHOP-ID") Long shopId) {
        ShopInfoVO shopInfoVO = shopService.getShopInfo(shopId);
        return ResponseEntity.ok(shopInfoVO);
    }

}
