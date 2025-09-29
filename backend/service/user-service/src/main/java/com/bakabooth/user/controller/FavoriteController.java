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
    public ResponseEntity<Void> favorite(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable("itemId") Long itemId
    ) {
        favoriteService.favorite(userId, itemId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "取消收藏商品")
    @DeleteMapping("/unfavorite/{itemId}")
    public ResponseEntity<Void> unfavorite(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable("itemId") Long itemId
    ) {
        favoriteService.unfavorite(userId, itemId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "获取收藏商品数量")
    @GetMapping("/favorite-count/{itemId}")
    public ResponseEntity<Long> getFavoriteCount(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable("itemId") Long itemId
    ) {
        Long favorites = favoriteService.getFavoriteCount(userId, itemId);
        return ResponseEntity.ok(favorites);
    }

    @Operation(summary = "商品是否已收藏")
    @GetMapping("/is-favorite/{itemId}")
    public ResponseEntity<Boolean> isFavorite(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable("itemId") Long itemId
    ) {
        Boolean isFavorite = favoriteService.isFavorite(userId, itemId);
        return ResponseEntity.ok(isFavorite);
    }
}
