package com.tamakara.booth.backend.service.user.service;

import com.tamakara.booth.backend.service.user.domain.dto.LoginFormDTO;
import com.tamakara.booth.backend.service.user.domain.dto.RegisterFormDTO;
import com.tamakara.booth.backend.service.user.domain.entity.User;
import com.tamakara.booth.backend.service.user.domain.vo.UserVO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {
    String login(LoginFormDTO loginFormDTO);

    String register(RegisterFormDTO registerFormDTO);

    UserVO getUserVO(Long userId, Long sellerId);

    Boolean pay(Long userId, Long sellerId, Double amount);
}
