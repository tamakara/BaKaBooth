package com.bakabooth.item.mapper;

import com.bakabooth.item.domain.entity.Image;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface ImageMapper extends BaseMapper<Image> {

    default List<Image> selectImagesByItemId(Long itemId) {
        return selectList(
                new LambdaQueryWrapper<Image>()
                        .eq(Image::getItemId, itemId)
                        .orderByAsc(Image::getOrderIndex)
        );
    }

}
