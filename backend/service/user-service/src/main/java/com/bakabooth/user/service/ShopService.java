package com.bakabooth.user.service;

import com.bakabooth.user.domain.vo.ShopInfoVO;

public interface ShopService {
    ShopInfoVO getShopInfo(Long userId);
}