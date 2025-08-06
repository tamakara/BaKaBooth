package com.bakabooth.item.service.impl;

import com.bakabooth.common.client.UserClient;
import com.bakabooth.common.domain.dto.UserDTO;
import com.bakabooth.item.domain.entity.Item;
import com.bakabooth.item.mapper.ItemMapper;
import com.bakabooth.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
