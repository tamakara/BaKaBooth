package com.bakabooth.shop.service;

import com.bakabooth.shop.domain.vo.ShopInfoVO;

public interface ShopService {
    Long create(Long userId);

    ShopInfoVO getShopInfo(Long shopId);
}