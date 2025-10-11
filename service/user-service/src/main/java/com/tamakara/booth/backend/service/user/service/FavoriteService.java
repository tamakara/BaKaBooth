package com.tamakara.booth.backend.service.user.service;

import com.tamakara.booth.backend.service.user.domain.entity.Favorite;
import com.baomidou.mybatisplus.extension.service.IService;

public interface FavoriteService extends IService<Favorite> {
    Long getFavoriteCount(Long userId, Long itemId);

    void favorite(Long userId, Long itemId);

    void unfavorite(Long userId, Long itemId);

    Boolean isFavorite(Long userId, Long itemId);
}
