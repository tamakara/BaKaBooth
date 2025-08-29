package com.bakabooth.item.mapper;

import com.bakabooth.item.domain.entity.Item;
import com.bakabooth.item.domain.vo.ItemEditFormVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface ItemMapper extends BaseMapper<Item> {

    default List<Item> getItemListByShopId(Long shopId) {
        return selectList(
                new LambdaQueryWrapper<Item>()
                        .eq(Item::getShopId, shopId));
    }

    default void updateItem( Long itemId, ItemEditFormVO vo) {
        update(null, new LambdaUpdateWrapper<Item>()
                .eq(Item::getId, itemId)
                .set(Item::getState, vo.getState())
                .set(Item::getName, vo.getName())
                .set(Item::getDescription, vo.getDescription())
        );
    }

}
