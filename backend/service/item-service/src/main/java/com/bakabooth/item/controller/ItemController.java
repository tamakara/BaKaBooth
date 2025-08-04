package com.bakabooth.item.controller;

import com.bakabooth.item.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "商品接口")
@RestController
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @Operation(summary = "创建商品")
    @PostMapping("/create")
    public ResponseEntity<Long> createItem(@RequestHeader("X-UID") String userId) {
        Long itemId = itemService.create(userId);
        return ResponseEntity.ok(itemId);
    }

}
