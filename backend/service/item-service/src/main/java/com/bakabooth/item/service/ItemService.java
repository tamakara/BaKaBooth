package com.bakabooth.item.service;

import com.bakabooth.item.domain.vo.ItemEditFormVO;
import com.bakabooth.item.domain.vo.ItemManageVO;

import java.util.List;

public interface ItemService {

    Long createItem(Long shopId);

    void updateItem(Long shopId, Long itemId,ItemEditFormVO itemEditFormVO);

    List<ItemManageVO> getItemManageVO(Long userId,Long shopId,String status);

    ItemEditFormVO getItemEditFormVO(Long shopId, Long itemId);


}
