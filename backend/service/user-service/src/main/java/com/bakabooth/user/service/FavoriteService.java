package com.bakabooth.user.service;

import com.bakabooth.user.domain.entity.Favorite;
import com.baomidou.mybatisplus.extension.service.IService;

public interface FavoriteService extends IService<Favorite> {
    Long getFavoriteCount(Long userId, Long itemId);

    void favoriteItem(Long userId, Long itemId);

    void unFavoriteItem(Long userId, Long itemId);

    Boolean isFavorite(Long userId, Long itemId);
}
