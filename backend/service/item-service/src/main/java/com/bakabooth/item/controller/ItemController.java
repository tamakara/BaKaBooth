package com.bakabooth.item.controller;

import com.bakabooth.item.domain.vo.ItemManageVO;
import com.bakabooth.item.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

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

    @Operation(summary = "创建商品")
    @GetMapping("/vo/item-manage")
    public ResponseEntity<List<ItemManageVO>> getItemManageVO(@RequestHeader("X-UID") Long userId) {
        List<ItemManageVO> itemManageVOList = itemService.getItemManageVO(userId);
        return ResponseEntity.ok(itemManageVOList);
    }
}
