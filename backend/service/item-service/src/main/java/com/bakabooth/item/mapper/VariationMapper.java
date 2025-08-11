package com.bakabooth.item.mapper;

import com.bakabooth.item.domain.entity.Variation;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface VariationMapper extends BaseMapper<Variation> {

    default List<Variation> selectVariationsByItemId(Long itemId) {
        return selectList(
                new LambdaQueryWrapper<Variation>()
                        .eq(Variation::getItemId, itemId)
                        .orderByAsc(Variation::getOrderIndex)
        );
    }
}
