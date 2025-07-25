package com.bakabooth.user.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.bakabooth.user.domain.dto.LoginFormDTO;
import com.bakabooth.user.domain.entity.User;
import com.bakabooth.user.domain.vo.UserLoginVO;
import com.bakabooth.user.service.IUserService;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl extends ServiceImpl<BaseMapper<User>, User> implements IUserService {

    @Override
    @Transactional
    public UserLoginVO login(LoginFormDTO loginFormDTO) {

        if (loginFormDTO.getUsername() == null||loginFormDTO.getPassword() == null) {
            throw new RuntimeException("用户名或密码不能为空");
        }

        User user = lambdaQuery()
                .eq(User::getUsername, loginFormDTO.getUsername())
                .or()
                .eq(User::getEmail, loginFormDTO.getUsername())
                .eq(User::getPassword, loginFormDTO.getPassword())
                .one();

        if(user == null) {
            throw new RuntimeException("用户名或密码错误");
        }

        StpUtil.login(user.getId());

        return new UserLoginVO( StpUtil.getTokenValue(),StpUtil.isLogin(),"user");
    }
}
