package com.bakabooth.item.converter;

import com.bakabooth.item.domain.entity.*;
import com.bakabooth.item.domain.vo.ItemEditFormVO;
import com.bakabooth.item.domain.vo.ItemManageVO;
import com.bakabooth.item.mapper.FileMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ItemConverter {
    private final VariationsConverter variationsConverter;
    private final FileMapper fileMapper;

    public ItemManageVO toItemManageVO(Item item) {
        ItemManageVO vo = new ItemManageVO();
        BeanUtils.copyProperties(item, vo);
        return vo;
    }

    public ItemEditFormVO toItemEditFormVO(Item item, List<Image> images, List<Tag> tags, List<Variation> variations) {
        ItemEditFormVO vo = new ItemEditFormVO();
        BeanUtils.copyProperties(item, vo);
        vo.setImages(images.stream().map(Image::getHash).toList());
        vo.setTags(tags.stream().map(Tag::getName).toList());
        vo.setVariations(variations.stream().map(variation -> {
            List<File> files = fileMapper.selectList(
                    new LambdaQueryWrapper<File>()
                            .eq(File::getVariationId, variation.getId()));
            return variationsConverter.toVariationsEditFormVO(variation, files);
        }).toList());
        return vo;
    }
}
