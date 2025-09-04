package com.bakabooth.user.service.impl;

import com.bakabooth.user.domain.entity.Shop;
import com.bakabooth.user.domain.vo.ShopInfoVO;
import com.bakabooth.user.mapper.ShopMapper;
import com.bakabooth.user.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {
    private final ShopMapper shopMapper;

    @Override
    public ShopInfoVO getShopInfo(Long userId) {
        Shop shop = shopMapper.selectByUserId(userId);
        return shop.toShopInfoVO();
    }
}
