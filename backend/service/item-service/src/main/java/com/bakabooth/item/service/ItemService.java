package com.bakabooth.item.service;

import com.bakabooth.item.domain.vo.ItemEditFormVO;
import com.bakabooth.item.domain.vo.ItemManageVO;

import java.util.List;

public interface ItemService {

    Long create(Long userId);

    List<ItemManageVO> getItemManageVO(Long userId);

    ItemEditFormVO getItemEditFormVO(Long userId, Long itemId);
}
