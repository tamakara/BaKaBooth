package com.bakabooth.item.service.impl;

import com.bakabooth.item.converter.ItemConverter;
import com.bakabooth.item.domain.entity.*;
import com.bakabooth.item.domain.vo.ItemEditFormVO;
import com.bakabooth.item.domain.vo.ItemManageVO;
import com.bakabooth.item.domain.vo.ItemVO;
import com.bakabooth.item.domain.vo.VariationEditFormVO;
import com.bakabooth.item.mapper.*;
import com.bakabooth.item.service.ItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements ItemService {
    private final ItemMapper itemMapper;
    private final ImageMapper imageMapper;
    private final TagMapper tagMapper;
    private final VariationMapper variationMapper;
    private final ItemConverter itemConverter;

    @Override
    @Transactional
    public Long createItem(Long userId) {
        Item item = new Item(userId);
        itemMapper.insert(item);
        variationMapper.insert(new Variation(item.getId()));

        return item.getId();
    }

    @Override
    @Transactional
    public Long updateItem(Long userId, Long itemId, ItemEditFormVO itemEditFormVO) {
        Item item = itemMapper.selectById(itemId);
        if (!item.getUserId().equals(userId))
            throw new RuntimeException("没有权限");
        lambdaUpdate()
                .eq(Item::getId, itemId)
                .set(Item::getStateCode, 0)
                .update();

        Item newItem = new Item(userId);
        BeanUtils.copyProperties(itemEditFormVO, newItem);
        save(newItem);

        Long newItemId = newItem.getId();

        List<Long> images = itemEditFormVO.getImages();
        for (int index = 0; index < images.size(); index++) {
            Image image = new Image();
            image.setItemId(newItemId);
            image.setFileId(images.get(index));
            image.setOrderIndex(index);
            imageMapper.insert(image);
        }

        List<String> tags = itemEditFormVO.getTags();
        for (int index = 0; index < tags.size(); index++) {
            Tag tag = new Tag();
            tag.setItemId(newItemId);
            tag.setName(tags.get(index));
            tag.setOrderIndex(index);
            tagMapper.insert(tag);
        }

        List<VariationEditFormVO> variations = itemEditFormVO.getVariations();
        for (int index = 0; index < variations.size(); index++) {
            VariationEditFormVO vo = variations.get(index);

            Variation variation = new Variation();
            variation.setItemId(newItemId);
            variation.setOrderIndex(index);
            variation.setName(vo.getName());
            variation.setPrice(vo.getPrice());
            variation.setStock(vo.getStock());
            variationMapper.insert(variation);
        }

        return newItemId;
    }

    @Override
    public List<ItemManageVO> getItemManageVO(Long userId, Integer stateCode) {
        return lambdaQuery()
                .eq(Item::getUserId, userId)
                .eq(!stateCode.equals(0), Item::getStateCode, stateCode)
                .list()
                .stream()
                .map(itemConverter::toItemManageVO)
                .toList();
    }

    @Override
    public ItemEditFormVO getItemEditFormVO(Long userId, Long itemId) {
        Item item = itemMapper.selectById(itemId);
        if (item == null || item.getUserId().longValue() != userId.longValue())
            throw new RuntimeException("获取商品信息失败");

        List<Image> images = imageMapper.selectImagesByItemId(itemId);
        List<Tag> tags = tagMapper.selectTagsByItemId(itemId);
        List<Variation> variations = variationMapper.selectVariationsByItemId(itemId);

        return itemConverter.toItemEditFormVO(item, images, tags, variations);
    }

    @Override
    public ItemVO getItemVO(Long userId, Long itemId) {
        Item item = itemMapper.selectById(itemId);
        if (item == null)
            throw new RuntimeException("获取商品信息失败");

        List<Image> images = imageMapper.selectImagesByItemId(itemId);
        List<Tag> tags = tagMapper.selectTagsByItemId(itemId);
        List<Variation> variations = variationMapper.selectVariationsByItemId(itemId);

        return itemConverter.toItemVO(item, images, tags, variations);
    }
}
