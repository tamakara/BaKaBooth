package com.bakabooth.item.service;

import com.bakabooth.item.domain.entity.Item;
import com.bakabooth.item.domain.vo.ItemEditFormVO;
import com.bakabooth.item.domain.vo.ItemVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ItemService extends IService<Item> {
    Long createItem(Long userId, ItemEditFormVO formVO);

    Boolean updateItem(Long userId, Long itemId, ItemEditFormVO itemEditFormVO);

    Boolean deleteItem(Long userId, Long itemId);

    ItemVO getItemVO(Long userId, Long itemId);

    List<ItemVO> getItemVOList(Long userId, Long sellerId, Integer stateCode, Integer pageNo, Integer pageSize);

    Boolean takeDownItem(Long userId, Long itemId);

    Boolean putUpItem(Long userId, Long itemId);
}
