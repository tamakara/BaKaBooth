package com.bakabooth.item.mapper;

import com.bakabooth.item.domain.entity.Item;
import com.bakabooth.item.domain.vo.ItemEditFormVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface ItemMapper extends BaseMapper<Item> {

    default List<Item> getItemListByUserIdAndStateCode(Long userId, Integer stateCode) {
        return selectList(
                new LambdaQueryWrapper<Item>()
                        .eq(Item::getUserId, userId)
                        .eq(Item::getStateCode, stateCode)
        );
    }

    default void updateItem(Long itemId, ItemEditFormVO vo) {

        update(null, new LambdaUpdateWrapper<Item>()
                .eq(Item::getId, itemId)
                .set(Item::getStateCode, vo.getStateCode())
                .set(Item::getName, vo.getName())
                .set(Item::getDescription, vo.getDescription())
                .set(Item::getDeliveryTime, vo.getDeliveryTime())
                .set(Item::getShippingDetails, vo.getShippingDetails())
                .set(Item::getReturnPolicy, vo.getReturnPolicy())
                .set(Item::getWarrantyPeriod, vo.getWarrantyPeriod()));
    }

}
