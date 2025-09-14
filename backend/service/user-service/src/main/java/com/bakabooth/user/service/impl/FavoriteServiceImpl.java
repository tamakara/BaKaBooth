package com.bakabooth.user.service.impl;

import com.bakabooth.user.domain.entity.Favorite;
import com.bakabooth.user.mapper.FavoriteMapper;
import com.bakabooth.user.service.FavoriteService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl extends ServiceImpl<FavoriteMapper, Favorite> implements FavoriteService {

    @Override
    public Long favoriteItem(Long userId, Long itemId) {

        Favorite favorite = lambdaQuery().eq(Favorite::getUserId, userId).eq(Favorite::getItemId, itemId).one();

        if (favorite == null) {
            save(new Favorite(userId, itemId));
        } else {
            removeById(favorite.getId());
        }

        return count(new LambdaQueryWrapper<Favorite>().eq(Favorite::getUserId, userId));
    }

    @Override
    public Boolean isFavorite(Long userId, Long itemId) {
        return exists(new LambdaQueryWrapper<Favorite>().eq(Favorite::getUserId, userId).eq(Favorite::getItemId, itemId));
    }


}
