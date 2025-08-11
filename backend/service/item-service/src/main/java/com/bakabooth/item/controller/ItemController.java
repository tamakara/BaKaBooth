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

@Tag(name = "商品接口")
@RestController
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @Operation(summary = "创建商品")
    @PostMapping("/create")
    public ResponseEntity<Long> createItem(@RequestHeader("X-UID") Long userId) {
        Long itemId = itemService.create(userId);
        return ResponseEntity.ok(itemId);
    }

    @Operation(summary = "获取商品列表")
    @GetMapping("/vo/item-manage")
    public ResponseEntity<List<ItemManageVO>> getItemManageVO(@RequestHeader("X-UID") Long userId) {
        List<ItemManageVO> itemManageVOList = itemService.getItemManageVO(userId);
        return ResponseEntity.ok(itemManageVOList);
    }

    @Operation(summary = "获取商品编辑表单信息")
    @GetMapping("/vo/item-edit-form/{item_id}")
    public ResponseEntity<ItemEditFormVO> getItemEditFormVO(
            @RequestHeader("X-UID") Long userId,
            @PathVariable("item_id") Long itemId
    ) {
        ItemEditFormVO itemEditFormVO = itemService.getItemEditFormVO(userId,itemId);
        return ResponseEntity.ok(itemEditFormVO);
    }
}
