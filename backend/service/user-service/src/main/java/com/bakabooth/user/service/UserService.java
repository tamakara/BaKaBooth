package com.bakabooth.user.service;

import com.bakabooth.user.domain.dto.LoginFormDTO;
import com.bakabooth.user.domain.dto.RegisterFormDTO;
import com.bakabooth.user.domain.vo.SellerVO;
import com.bakabooth.user.domain.vo.ShopManageVO;
import com.bakabooth.user.domain.vo.UserSimpleInfoVO;

public interface UserService {
    String login(LoginFormDTO loginFormDTO);

    String register(RegisterFormDTO registerFormDTO);

    UserSimpleInfoVO getUserSimpleInfoVO(Long userId);

    ShopManageVO getShopManageVO(Long userId);

    SellerVO getSellerUserVO(Long sellerUserId);
}
