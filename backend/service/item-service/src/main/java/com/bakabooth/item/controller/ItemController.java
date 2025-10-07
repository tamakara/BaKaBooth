package com.bakabooth.item.controller;

import com.bakabooth.item.domain.vo.ItemEditFormVO;
import com.bakabooth.item.domain.vo.ItemPageVO;
import com.bakabooth.item.domain.vo.ItemQueryFormVO;
import com.bakabooth.item.domain.vo.ItemVO;
import com.bakabooth.item.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "商品接口")
@RestController
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @Operation(summary = "创建商品")
    @PostMapping("/create")
    public ResponseEntity<Long> createItem(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestBody ItemEditFormVO formVO
    ) {
        Long itemId = itemService.createItem(userId, formVO);
        return ResponseEntity.ok(itemId);
    }

    @Operation(summary = "更新商品信息")
    @PutMapping("/update/{itemId}")
    public ResponseEntity<Boolean> updateItem(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable("itemId") Long itemId,
            @RequestBody ItemEditFormVO formVO
    ) {
        Boolean isUpdate = itemService.updateItem(userId, itemId, formVO);
        return ResponseEntity.ok(isUpdate);
    }

    @Operation(summary = "删除商品")
    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity<Boolean> deleteItem(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable("itemId") Long itemId
    ) {
        Boolean isDelete = itemService.deleteItem(userId, itemId);
        return ResponseEntity.ok(isDelete);
    }

    @Operation(summary = "获取商品信息")
    @GetMapping("/vo/item/{itemId}")
    public ResponseEntity<ItemVO> getItemVO(
            @RequestHeader(name = "X-USER-ID", required = false) Long userId,
            @PathVariable("itemId") Long itemId
    ) {
        ItemVO vo = itemService.getItemVO(userId, itemId);
        return ResponseEntity.ok(vo);
    }

    @Operation(summary = "获取商品信息列表")
    @GetMapping("/vo/items")
    public ResponseEntity<ItemPageVO> getItemPageVO(
            @RequestHeader(name = "X-USER-ID", required = false) Long userId,
            @ModelAttribute ItemQueryFormVO formVO
    ) {
        ItemPageVO itemPageVO = itemService.getItemPageVO(userId, formVO);
        return ResponseEntity.ok(itemPageVO);
    }

    @Operation(summary = "下架商品")
    @PutMapping("/take-down/{itemId}")
    public ResponseEntity<Boolean> takeDownItem(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable("itemId") Long itemId
    ) {
        Boolean isTakeDown = itemService.takeDownItem(userId, itemId);
        return ResponseEntity.ok(isTakeDown);
    }

    @Operation(summary = "上架商品")
    @PutMapping("/put-up/{itemId}")
    public ResponseEntity<Boolean> putUpItem(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable("itemId") Long itemId
    ) {
        Boolean isTakeUp = itemService.putUpItem(userId, itemId);
        return ResponseEntity.ok(isTakeUp);
    }

    @Operation(summary = "锁定商品")
    @PutMapping("/lock/{itemId}")
    public ResponseEntity<Boolean> lockItem(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable("itemId") Long itemId
    ) {
        if (userId != 0) {
            throw new RuntimeException("只有管理员可以锁定商品");
        }
        Boolean isLock = itemService.lockItem(itemId);
        return ResponseEntity.ok(isLock);
    }
}