package com.bakabooth.user.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bakabooth.user.config.JwtProperties;
import com.bakabooth.user.domain.dto.LoginFormDTO;
import com.bakabooth.user.domain.entity.User;
import com.bakabooth.user.domain.vo.UserInfoVO;
import com.bakabooth.user.domain.vo.UserLoginVO;
import com.bakabooth.user.service.IUserService;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<BaseMapper<User>, User> implements IUserService {
    private final JwtProperties jwtProperties;

    @Override
    @Transactional
    public String login(LoginFormDTO loginFormDTO) {
        if (loginFormDTO.getUsername() == null || loginFormDTO.getPassword() == null) {
            throw new RuntimeException("用户名或密码不能为空");
        }

        User user = lambdaQuery()
                .eq(User::getUsername, loginFormDTO.getUsername())
                .or()
                .eq(User::getEmail, loginFormDTO.getUsername())
                .eq(User::getPassword, loginFormDTO.getPassword())
                .one();

        if (user == null) {
            throw new RuntimeException("用户名或密码错误");
        }

        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecret());
        String token = JWT.create()
                .withSubject(user.getId().toString())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getExpiration() * 1000))
                .sign(algorithm);

        return token;
    }
}
