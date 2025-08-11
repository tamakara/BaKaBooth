package com.bakabooth.item.mapper;

import com.bakabooth.item.domain.entity.Item;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface ItemMapper extends BaseMapper<Item> {

    default List<Item> getItemListByShopId(Long shopId) {
        return selectList(
                new LambdaQueryWrapper<Item>()
                        .eq(Item::getShopId, shopId));
    }

}
