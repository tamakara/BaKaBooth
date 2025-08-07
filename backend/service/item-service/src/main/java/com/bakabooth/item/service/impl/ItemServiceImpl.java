package com.bakabooth.item.service.impl;

import com.bakabooth.common.client.UserClient;
import com.bakabooth.common.domain.dto.UserDTO;
import com.bakabooth.item.domain.entity.Item;
import com.bakabooth.item.domain.vo.ItemManageVO;
import com.bakabooth.item.mapper.ItemMapper;
import com.bakabooth.item.service.ItemService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final UserClient userClient;
    private final ItemMapper itemMapper;

    @Override
    public Long create(Long userId) {
        UserDTO userDTO = userClient.getUserDTO(userId).getBody();
        if (userDTO == null) throw new RuntimeException("获取UserDTO失败");
        Item item = new Item(userDTO.getShopId());
        itemMapper.insert(item);
        return item.getId();
    }

    @Override
    public List<ItemManageVO> getItemManageVO(Long userId) {
        UserDTO userDTO = userClient.getUserDTO(userId).getBody();
        if (userDTO == null) throw new RuntimeException("获取UserDTO失败");
        List<Item> itemList = itemMapper.getItemListByShopId(userDTO.getShopId());
        List<ItemManageVO> itemManageVOList = itemList
                .stream()
                .map(item -> {
                    ItemManageVO vo = new ItemManageVO();
                    BeanUtils.copyProperties(item, vo);
                    return vo;
                })
                .toList();
        return itemManageVOList;
    }
}
