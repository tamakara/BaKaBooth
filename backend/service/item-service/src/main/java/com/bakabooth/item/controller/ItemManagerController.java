package com.bakabooth.item.controller;

import com.bakabooth.item.domain.vo.ItemEditFormVO;
import com.bakabooth.item.domain.vo.ItemManageVO;
import com.bakabooth.item.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "商品管理接口")
@RestController
@RequiredArgsConstructor
public class ItemManagerController {
    private final ItemService itemService;

    @Operation(summary = "创建商品")
    @PostMapping("/create")
    public ResponseEntity<Long> createItem(
            @RequestHeader("X-USER-ID") Long userId
    ) {
        Long itemId = itemService.createItem(userId);
        return ResponseEntity.ok(itemId);
    }

    @Operation(summary = "获取商品列表")
    @GetMapping("/vo/item-manage")
    public ResponseEntity<List<ItemManageVO>> getItemManageVO(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestParam("stateCode") Integer stateCode
    ) {
        List<ItemManageVO> itemManageVOList = itemService.getItemManageVO(userId, stateCode);
        return ResponseEntity.ok(itemManageVOList);
    }

    @Operation(summary = "获取商品编辑表单信息")
    @GetMapping("/vo/item-edit-form/{itemId}")
    public ResponseEntity<ItemEditFormVO> getItemEditFormVO(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable("itemId") Long itemId
    ) {
        ItemEditFormVO itemEditFormVO = itemService.getItemEditFormVO(userId, itemId);
        return ResponseEntity.ok(itemEditFormVO);
    }

    @Operation(summary = "更新商品信息")
    @PutMapping("/update/{itemId}")
    public ResponseEntity<Long> updateItem(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable("itemId") Long itemId,
            @RequestBody ItemEditFormVO itemEditFormVO
    ) {
        Long newItemId = itemService.updateItem(userId, itemId, itemEditFormVO);
        return ResponseEntity.ok(newItemId);
    }
}
