package com.bakabooth.item.service.impl;

import com.bakabooth.item.converter.ItemConverter;
import com.bakabooth.item.domain.entity.*;
import com.bakabooth.item.domain.vo.ItemEditFormVO;
import com.bakabooth.item.domain.vo.ItemManageVO;
import com.bakabooth.item.domain.vo.ItemVO;
import com.bakabooth.item.mapper.*;
import com.bakabooth.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
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
    public void updateItem(Long userId, Long itemId, ItemEditFormVO itemEditFormVO) {
        Item item = itemMapper.selectById(itemId);
        if (!item.getUserId().equals(userId))
            throw new RuntimeException("没有权限");

        itemMapper.updateItem(itemId, itemEditFormVO);
        imageMapper.updateImages(itemId, itemEditFormVO.getImages());
        tagMapper.updateTags(itemId, itemEditFormVO.getTags());
        variationMapper.updateVariations(itemId, itemEditFormVO.getVariations());
    }

    @Override
    public List<ItemManageVO> getItemManageVO(Long userId, String state) {
        List<Item> itemList = itemMapper.getItemListByUserIdAndState(userId, state);
        return itemList
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
