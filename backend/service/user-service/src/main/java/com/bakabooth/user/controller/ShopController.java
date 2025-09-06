package com.bakabooth.user.controller;

import com.bakabooth.user.domain.vo.ShopManagePageVO;
import com.bakabooth.user.domain.vo.ShopVO;
import com.bakabooth.user.service.ShopService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ShopController {
    private final ShopService shopService;

    @Operation(summary = "获取店铺管理信息")
    @GetMapping("/vo/shop-manage-page")
    public ResponseEntity<ShopManagePageVO> getShopManagePageVO(
            @RequestHeader("X-USER-ID") Long userId
    ) {
        ShopManagePageVO vo = shopService.getShopManagePageVO(userId);
        return ResponseEntity.ok(vo);
    }

    @Operation(summary = "获取店铺信息")
    @GetMapping("/vo/shop/{shopId}")
    public ResponseEntity<ShopVO> getShopVO(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable("shopId") Long shopId
    ) {
        ShopVO vo = shopService.getShopVO(shopId);
        return ResponseEntity.ok(vo);
    }
}
