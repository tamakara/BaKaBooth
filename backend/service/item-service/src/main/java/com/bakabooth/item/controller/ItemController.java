package com.bakabooth.item.controller;

import com.bakabooth.item.domain.vo.ItemVO;
import com.bakabooth.item.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "商品接口")
@RestController
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @Operation(summary = "获取商品信息")
    @GetMapping("/vo/item/{itemId}")
    public ResponseEntity<ItemVO> getItemVO(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable("itemId") Long itemId
    ) {
        ItemVO vo = itemService.getItemVO(userId, itemId);
        return ResponseEntity.ok(vo);
    }


}
