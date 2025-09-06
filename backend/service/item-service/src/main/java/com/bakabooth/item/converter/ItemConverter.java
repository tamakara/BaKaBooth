package com.bakabooth.item.converter;

import com.bakabooth.common.client.FileClient;
import com.bakabooth.common.domain.dto.FileDTO;
import com.bakabooth.item.domain.entity.*;
import com.bakabooth.item.domain.vo.ItemEditFormVO;
import com.bakabooth.item.domain.vo.ItemManageVO;
import com.bakabooth.item.domain.vo.ItemVO;
import com.bakabooth.item.mapper.FileMapper;
import com.bakabooth.item.mapper.ImageMapper;
import com.bakabooth.item.mapper.VariationMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ItemConverter {
    private final VariationsConverter variationsConverter;
    private final VariationMapper variationMapper;
    private final ImageMapper imageMapper;
    private final FileMapper fileMapper;
    private final FileClient fileClient;

    public ItemManageVO toItemManageVO(Item item) {
        ItemManageVO vo = new ItemManageVO();
        BeanUtils.copyProperties(item, vo);

        List<Image> images = imageMapper.selectImagesByItemId(item.getId());
        if (!images.isEmpty()) {
            FileDTO fileDTO = fileClient.getFileUrl(0L, images.get(0).getFileId()).getBody();
            if (fileDTO == null) throw new RuntimeException("获取封面图片失败");
            vo.setCoverUrl(fileDTO.getUrl());
        }

        List<Variation> variations = variationMapper.selectVariationsByItemId(item.getId());
        vo.setVariations(variations);
        return vo;
    }

    public ItemEditFormVO toItemEditFormVO(Item item, List<Image> images, List<Tag> tags, List<Variation> variations) {
        ItemEditFormVO vo = new ItemEditFormVO();
        BeanUtils.copyProperties(item, vo);
        vo.setImages(images.stream().map(Image::getFileId).toList());
        vo.setTags(tags.stream().map(Tag::getName).toList());
        vo.setVariations(variations.stream().map(variation -> {
            List<File> files = fileMapper.selectList(
                    new LambdaQueryWrapper<File>()
                            .eq(File::getVariationId, variation.getId()));
            return variationsConverter.toVariationsEditFormVO(variation, files);
        }).toList());
        return vo;
    }

    public ItemVO toItemVO(Item item, List<Image> images, List<Tag> tags, List<Variation> variations) {
        ItemVO vo = new ItemVO();
        BeanUtils.copyProperties(item, vo);

        vo.setImageUrls(images.stream().map(image -> {
                    FileDTO fileDTO = fileClient.getFileUrl(0L, image.getFileId()).getBody();
                    if (fileDTO == null) throw new RuntimeException("获取封面图片失败");
                    return fileDTO.getUrl();
                }).toList()
        );
        vo.setTags(tags.stream().map(Tag::getName).toList());
        vo.setVariations(variations.stream().map(variationsConverter::toVariationVO).toList());

        return vo;
    }
}
