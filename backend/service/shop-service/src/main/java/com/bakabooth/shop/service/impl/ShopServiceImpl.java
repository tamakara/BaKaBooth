package com.bakabooth.shop.service.impl;

import com.bakabooth.shop.domain.entity.Shop;
import com.bakabooth.shop.domain.vo.ShopInfoVO;
import com.bakabooth.shop.mapper.ShopMapper;
import com.bakabooth.shop.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {
    private final ShopMapper shopMapper;

    @Override
    public ShopInfoVO getShopInfo(Long shopId) {
        Shop shop = shopMapper.selectById(shopId);
        return shop.toShopInfoVO();
    }
}
