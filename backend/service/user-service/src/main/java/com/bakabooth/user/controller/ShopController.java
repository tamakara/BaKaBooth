package com.bakabooth.user.controller;

import com.bakabooth.user.domain.vo.ShopManagePageVO;
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
    @GetMapping("/vo/shop-manage-page")
    public ResponseEntity<ShopManagePageVO> getShopManagePageVO(
            @RequestHeader("X-USER-ID") Long userId
    ) {
        ShopManagePageVO shopManagePageVO = shopService.ShopManagePageVO(userId);
        return ResponseEntity.ok(shopManagePageVO);
    }
}
