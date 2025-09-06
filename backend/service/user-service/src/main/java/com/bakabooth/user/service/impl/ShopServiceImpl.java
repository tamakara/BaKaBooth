package com.bakabooth.user.service.impl;

import com.bakabooth.user.converter.ShopConverter;
import com.bakabooth.user.domain.entity.Shop;
import com.bakabooth.user.domain.entity.User;
import com.bakabooth.user.domain.vo.ShopManagePageVO;
import com.bakabooth.user.domain.vo.ShopVO;
import com.bakabooth.user.mapper.ShopMapper;
import com.bakabooth.user.mapper.UserMapper;
import com.bakabooth.user.service.ShopService;
import com.bakabooth.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {
    private final UserMapper userMapper;
    private final ShopMapper shopMapper;
    private final ShopConverter shopConverter;

    @Override
    public ShopManagePageVO getShopManagePageVO(Long userId) {
        Shop shop = shopMapper.selectByUserId(userId);
        return shopConverter.toShopManagePageVO(shop);
    }

    @Override
    public ShopVO getShopVO(Long shopId) {
        User user = userMapper.selectById(shopId);
        Shop shop = shopMapper.selectByUserId(shopId);
        return shopConverter.toShopVO(user,shop);
    }
}
