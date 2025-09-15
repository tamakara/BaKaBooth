package com.bakabooth.user.controller;

import com.bakabooth.user.service.FavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "商品收藏接口")
@RestController
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;

    @Operation(summary = "收藏商品")
    @PostMapping("/favorite/{itemId}")
    public ResponseEntity<Boolean> favoriteItem(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable("itemId") Long itemId
    ) {
        Boolean isFavorite = favoriteService.favoriteItem(userId, itemId);
        return ResponseEntity.ok(isFavorite);
    }

    @Operation(summary = "获取收藏商品数量")
    @PostMapping("/favorite/count/{itemId}")
    public ResponseEntity<Long> getFavoriteItemCount(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable("itemId") Long itemId
    ) {
        Long favorites = favoriteService.getFavoriteCount(userId, itemId);
        return ResponseEntity.ok(favorites);
    }

    @Operation(summary = "商品是否已收藏")
    @GetMapping("/favorite/{itemId}")
    public ResponseEntity<Boolean> isFavorite(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable("itemId") Long itemId
    ) {
        Boolean isFavorite = favoriteService.isFavorite(userId, itemId);
        return ResponseEntity.ok(isFavorite);
    }
}
