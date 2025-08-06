package com.bakabooth.shop.service.impl;

import com.bakabooth.common.client.UserClient;
import com.bakabooth.common.domain.dto.UserDTO;
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
    private final UserClient userClient;

    @Override
    public ShopInfoVO getShopInfo(Long userId) {
        UserDTO userDTO = userClient.getUserDTO(userId).getBody();
        if (userDTO == null) throw new RuntimeException("用户信息获取失败");
        Shop shop = shopMapper.selectById(userDTO.getShopId());
        return shop.toShopInfoVO();
    }
}
