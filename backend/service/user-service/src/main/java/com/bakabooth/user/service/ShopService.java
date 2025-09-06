package com.bakabooth.user.service;

import com.bakabooth.user.domain.vo.ShopManagePageVO;
import com.bakabooth.user.domain.vo.ShopVO;

public interface ShopService {
    ShopManagePageVO getShopManagePageVO(Long userId);

    ShopVO getShopVO(Long shopId);
}