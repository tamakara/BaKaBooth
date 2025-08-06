package com.bakabooth.shop.service;

import com.bakabooth.shop.domain.vo.ShopInfoVO;

public interface ShopService {
    ShopInfoVO getShopInfo(Long userId);
}