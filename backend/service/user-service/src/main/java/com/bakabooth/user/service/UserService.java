package com.bakabooth.user.service;

import com.bakabooth.user.domain.dto.LoginFormDTO;
import com.bakabooth.user.domain.dto.RegisterFormDTO;
import com.bakabooth.user.domain.entity.User;
import com.bakabooth.user.domain.vo.UserVO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {
    String login(LoginFormDTO loginFormDTO);

    String register(RegisterFormDTO registerFormDTO);

    UserVO getUserVO(Long userId, Long sellerId);
}
