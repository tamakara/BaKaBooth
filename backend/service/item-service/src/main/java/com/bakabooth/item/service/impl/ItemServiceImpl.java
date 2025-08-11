package com.bakabooth.item.service.impl;

import com.bakabooth.common.client.UserClient;
import com.bakabooth.common.domain.dto.UserDTO;
import com.bakabooth.item.converter.ItemConverter;
import com.bakabooth.item.domain.entity.Image;
import com.bakabooth.item.domain.entity.Item;
import com.bakabooth.item.domain.entity.Tag;
import com.bakabooth.item.domain.entity.Variation;
import com.bakabooth.item.domain.vo.ItemEditFormVO;
import com.bakabooth.item.domain.vo.ItemManageVO;
import com.bakabooth.item.mapper.ImageMapper;
import com.bakabooth.item.mapper.ItemMapper;
import com.bakabooth.item.mapper.TagMapper;
import com.bakabooth.item.mapper.VariationMapper;
import com.bakabooth.item.service.ItemService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final UserClient userClient;
    private final ItemMapper itemMapper;
    private final ImageMapper imageMapper;
    private final TagMapper tagMapper;
    private final VariationMapper variationMapper;
    private final ItemConverter itemConverter;

    @Override
    @Transactional
    public Long create(Long userId) {
        UserDTO userDTO = userClient.getUserDTO(userId).getBody();
        if (userDTO == null) throw new RuntimeException("获取UserDTO失败");

        Item item = new Item(userDTO.getShopId());
        itemMapper.insert(item);
        variationMapper.insert(new Variation(item.getId()));

        return item.getId();
    }

    @Override
    public List<ItemManageVO> getItemManageVO(Long userId) {
        UserDTO userDTO = userClient.getUserDTO(userId).getBody();
        if (userDTO == null) throw new RuntimeException("获取UserDTO失败");
        List<Item> itemList = itemMapper.getItemListByShopId(userDTO.getShopId());
        List<ItemManageVO> itemManageVOList = itemList
                .stream()
                .map(itemConverter::toItemManageVO)
                .toList();
        return itemManageVOList;
    }

    @Override
    public ItemEditFormVO getItemEditFormVO(Long userId, Long itemId) {
        UserDTO userDTO = userClient.getUserDTO(userId).getBody();
        Item item = itemMapper.selectById(itemId);
        if (item == null || userDTO == null || item.getShopId().longValue() != userDTO.getShopId().longValue())
            throw new RuntimeException("获取商品信息失败");

        List<Image> images = imageMapper.selectImagesByItemId(itemId);
        List<Tag> tags = tagMapper.selectTagsByItemId(itemId);
        List<Variation> variations = variationMapper.selectVariationsByItemId(itemId);

        ItemEditFormVO itemEditFormVO = itemConverter.toItemEditFormVO(item, images, tags, variations);

        return itemEditFormVO;
    }
}
