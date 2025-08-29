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

    default void updateImages(Long itemId, List<Long> images) {
        delete(new LambdaQueryWrapper<Image>().eq(Image::getItemId, itemId));
        for (int index = 0; index < images.size(); index++) {
            Image image = new Image();
            image.setItemId(itemId);
            image.setFileId(images.get(index));
            image.setOrderIndex(index);
            insert(image);
        }
    }


}
