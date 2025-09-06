package com.bakabooth.item.service;

import com.bakabooth.item.domain.vo.ItemEditFormVO;
import com.bakabooth.item.domain.vo.ItemManageVO;
import com.bakabooth.item.domain.vo.ItemVO;

import java.util.List;

public interface ItemService {

    Long createItem(Long userId);

    void updateItem(Long userId, Long itemId, ItemEditFormVO itemEditFormVO);

    List<ItemManageVO> getItemManageVO(Long userId, String status);

    ItemEditFormVO getItemEditFormVO(Long userId, Long itemId);

    ItemVO getItemVO(Long userId, Long itemId);
}
