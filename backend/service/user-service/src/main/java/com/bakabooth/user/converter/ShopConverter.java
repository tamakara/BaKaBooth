package com.bakabooth.user.converter;

import com.bakabooth.common.client.FileClient;
import com.bakabooth.common.domain.dto.FileDTO;
import com.bakabooth.user.domain.entity.Shop;
import com.bakabooth.user.domain.entity.User;
import com.bakabooth.user.domain.vo.ShopManagePageVO;
import com.bakabooth.user.domain.vo.ShopVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShopConverter {
    private final FileClient fileClient;

    public ShopManagePageVO toShopManagePageVO(Shop shop) {
        ShopManagePageVO vo = new ShopManagePageVO();
        BeanUtils.copyProperties(shop, vo);
        return vo;
    }

    public ShopVO toShopVO(User user, Shop shop) {
        ShopVO vo = new ShopVO();
        BeanUtils.copyProperties(shop, vo);
        FileDTO fileDTO = fileClient.getFileUrl(0L, user.getAvatarFileId()).getBody();
        if (fileDTO == null) throw new RuntimeException("获取头像失败");
        vo.setAvatarUrl(fileDTO.getUrl());
        return vo;
    }
}
