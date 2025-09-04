package com.bakabooth.user.mapper;

import com.bakabooth.user.domain.entity.Shop;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShopMapper extends BaseMapper<Shop> {
    default Shop selectByUserId(Long userId) {
        return this.selectOne(new LambdaQueryWrapper<Shop>().eq(Shop::getUserId, userId));
    }
}
