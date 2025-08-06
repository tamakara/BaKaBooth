package com.bakabooth.user.service;

import com.bakabooth.common.domain.dto.UserDTO;
import com.bakabooth.user.domain.dto.LoginFormDTO;
import com.bakabooth.user.domain.vo.UserSimpleInfoVO;

public interface UserService {
    String login(LoginFormDTO loginFormDTO);

    UserSimpleInfoVO getUserSimpleInfoVO(Long userId);

    UserDTO getUserDTO(Long userId);
}
