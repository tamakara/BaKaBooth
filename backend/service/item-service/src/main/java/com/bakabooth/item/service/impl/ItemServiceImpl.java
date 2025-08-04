package com.bakabooth.item.service.impl;

import com.bakabooth.item.domain.entity.Item;
import com.bakabooth.item.mapper.ItemMapper;
import com.bakabooth.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemMapper itemMapper;

    @Override
    public Long create(String userId) {
        Item item = new Item(userId);
        itemMapper.insert(item);
        return item.getId();
    }
}
