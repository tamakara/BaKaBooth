package com.bakabooth.user.converter;

import com.bakabooth.common.client.FileClient;
import com.bakabooth.common.domain.dto.FileDTO;
import com.bakabooth.user.domain.entity.User;
import com.bakabooth.user.domain.vo.SellerVO;
import com.bakabooth.user.domain.vo.ShopManageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConverter {
    private final FileClient fileClient;

    public ShopManageVO toShopManagePageVO(User user) {
        ShopManageVO vo = new ShopManageVO();
        BeanUtils.copyProperties(user, vo);
        return vo;
    }

    public SellerVO toSellerUserVO(User user) {
        SellerVO vo = new SellerVO();
        BeanUtils.copyProperties(user, vo);

        FileDTO fileDTO = fileClient.getFileUrl(0L, user.getAvatarFileId()).getBody();
        if (fileDTO == null) throw new RuntimeException("获取url失败");
        vo.setAvatarUrl(fileDTO.getUrl());

        return vo;
    }

}
