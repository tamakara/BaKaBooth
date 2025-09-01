package com.bakabooth.item.service.impl;

import com.bakabooth.item.converter.ItemConverter;
import com.bakabooth.item.domain.entity.*;
import com.bakabooth.item.domain.vo.ItemEditFormVO;
import com.bakabooth.item.domain.vo.ItemManageVO;
import com.bakabooth.item.domain.vo.VariationsEditFormVO;
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
    private final FileMapper fileMapper;

    @Override
    @Transactional
    public Long createItem(Long shopId) {
        Item item = new Item(shopId);
        itemMapper.insert(item);
        variationMapper.insert(new Variation(item.getId()));

        return item.getId();
    }

    @Override
    @Transactional
    public void updateItem(Long shopId, Long itemId, ItemEditFormVO itemEditFormVO) {
        Item item = itemMapper.selectById(itemId);
        if (!item.getShopId().equals(shopId))
            throw new RuntimeException("没有权限");

        itemMapper.updateItem(itemId, itemEditFormVO);
        imageMapper.updateImages(itemId, itemEditFormVO.getImages());
        tagMapper.updateTags(itemId, itemEditFormVO.getTags());
        variationMapper.updateVariations(itemId, itemEditFormVO.getVariations());
        List<Variation> variations = variationMapper.selectVariationsByItemId(itemId);
        for (int index = 0; index < itemEditFormVO.getVariations().size(); index++) {
            List<Long> fileIds = itemEditFormVO.getVariations().get(index).getFiles();
            fileMapper.updateFiles(variations.get(index).getId(), fileIds);
        }
    }

    @Override
    public List<ItemManageVO> getItemManageVO(Long userId,Long shopId) {
        List<Item> itemList = itemMapper.getItemListByShopId(shopId);
        List<ItemManageVO> itemManageVOList = itemList
                .stream()
                .map(item -> itemConverter.toItemManageVO(userId,item))
                .toList();


        return itemManageVOList;
    }

    @Override
    public ItemEditFormVO getItemEditFormVO(Long shopId, Long itemId) {
        Item item = itemMapper.selectById(itemId);
        if (item == null || item.getShopId().longValue() != shopId.longValue())
            throw new RuntimeException("获取商品信息失败");

        List<Image> images = imageMapper.selectImagesByItemId(itemId);
        List<Tag> tags = tagMapper.selectTagsByItemId(itemId);
        List<Variation> variations = variationMapper.selectVariationsByItemId(itemId);

        return itemConverter.toItemEditFormVO(item, images, tags, variations);
    }
}
