package com.bakabooth.item.service;

import com.bakabooth.item.domain.entity.Item;
import com.bakabooth.item.domain.vo.ItemEditFormVO;
import com.bakabooth.item.domain.vo.ItemManageVO;
import com.bakabooth.item.domain.vo.ItemVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ItemService extends IService<Item> {

    Long createItem(Long userId);

    void updateItem(Long userId, Long itemId, ItemEditFormVO itemEditFormVO);

    List<ItemManageVO> getItemManageVO(Long userId, Integer stateCode);

    ItemEditFormVO getItemEditFormVO(Long userId, Long itemId);

    ItemVO getItemVO(Long userId, Long itemId);
}
