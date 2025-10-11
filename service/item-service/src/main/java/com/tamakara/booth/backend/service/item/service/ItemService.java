package com.tamakara.booth.backend.service.item.service;

import com.tamakara.booth.backend.service.item.domain.entity.Item;
import com.tamakara.booth.backend.service.item.domain.vo.ItemEditFormVO;
import com.tamakara.booth.backend.service.item.domain.vo.ItemPageVO;
import com.tamakara.booth.backend.service.item.domain.vo.ItemQueryFormVO;
import com.tamakara.booth.backend.service.item.domain.vo.ItemVO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ItemService extends IService<Item> {
    Long createItem(Long userId, ItemEditFormVO formVO);

    Boolean updateItem(Long userId, Long itemId, ItemEditFormVO formVO);

    Boolean deleteItem(Long userId, Long itemId);

    ItemVO getItemVO(Long userId, Long itemId);

    ItemPageVO getItemPageVO(Long userId, ItemQueryFormVO formVO);

    Boolean takeDownItem(Long userId, Long itemId);

    Boolean putUpItem(Long userId, Long itemId);

    Boolean lockItem(Long itemId);
}
