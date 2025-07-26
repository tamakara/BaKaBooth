package com.bakabooth.user.service;

import com.bakabooth.user.domain.dto.LoginFormDTO;
import com.bakabooth.user.domain.entity.User;
import com.bakabooth.user.domain.vo.UserLoginVO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IUserService extends IService<User> {
    String login(LoginFormDTO loginFormDTO);
}
