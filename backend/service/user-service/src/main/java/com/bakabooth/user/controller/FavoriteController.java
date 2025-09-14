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
    public ResponseEntity<Long> favoriteItem(@RequestHeader("X-USER-ID") Long userId, @PathVariable("itemId") Long itemId) {
        Long favorites = favoriteService.favoriteItem(userId, itemId);
        return ResponseEntity.ok(favorites);
    }

    @Operation(summary = "商品是否已收藏")
    @GetMapping("/favorite/{itemId}")
    public ResponseEntity<Long> isFavorite(@RequestHeader("X-USER-ID") Long userId, @PathVariable("itemId") Long itemId) {
        Long favorites = favoriteService.favoriteItem(userId, itemId);
        return ResponseEntity.ok(favorites);
    }

}
