package com.bakabooth.user.controller;

import com.bakabooth.user.domain.vo.ShopInfoVO;
import com.bakabooth.user.service.ShopService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ShopController {
    private final ShopService shopService;

    @Operation(summary = "获取店铺信息")
    @GetMapping("/shop/info")
    public ResponseEntity<ShopInfoVO> getShopInfo(@RequestHeader("X-USER-ID") Long userId) {
        ShopInfoVO shopInfoVO = shopService.getShopInfo(userId);
        return ResponseEntity.ok(shopInfoVO);
    }

}
