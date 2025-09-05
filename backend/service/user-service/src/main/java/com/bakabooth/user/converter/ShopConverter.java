package com.bakabooth.user.converter;

import com.bakabooth.user.domain.entity.Shop;
import com.bakabooth.user.domain.vo.ShopManagePageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ShopConverter {
    public ShopManagePageVO toShopManagePageVO(Shop shop) {
        ShopManagePageVO vo = new ShopManagePageVO();
        BeanUtils.copyProperties(shop, vo);
        return vo;
    }
}
