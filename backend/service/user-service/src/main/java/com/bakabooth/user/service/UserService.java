package com.bakabooth.user.service;

import com.bakabooth.user.domain.dto.LoginFormDTO;
import com.bakabooth.user.domain.vo.UserSimpleInfoVO;

public interface UserService {
    String login(LoginFormDTO loginFormDTO);

    UserSimpleInfoVO getUserSimpleInfo(Long userId);
}
