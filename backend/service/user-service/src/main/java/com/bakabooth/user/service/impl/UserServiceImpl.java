package com.bakabooth.user.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bakabooth.common.domain.dto.UserDTO;
import com.bakabooth.user.config.JwtProperties;
import com.bakabooth.user.domain.dto.LoginFormDTO;
import com.bakabooth.user.domain.entity.User;
import com.bakabooth.user.domain.vo.UserSimpleInfoVO;
import com.bakabooth.user.mapper.UserMapper;
import com.bakabooth.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final JwtProperties jwtProperties;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public String login(LoginFormDTO loginFormDTO) {
        if (loginFormDTO.getPhone() == null || loginFormDTO.getPassword() == null) {
            throw new RuntimeException("手机号或密码不能为空");
        }

        User user = userMapper.getUserByPhoneAndPassword(
                Integer.valueOf(loginFormDTO.getPhone()),
                loginFormDTO.getPassword()
        );

        if (user == null) throw new RuntimeException("手机号或密码错误");

        String userId = user.getId().toString();
        String jti = UUID.randomUUID().toString();
        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecret());

        String token = JWT.create()
                .withSubject(userId)
                .withExpiresAt(Instant
                        .now()
                        .plusSeconds(jwtProperties.getExpiration())
                )
                .withClaim("jti", jti)
                .sign(algorithm);

        return token;
    }


    @Override
    public UserSimpleInfoVO getUserSimpleInfoVO(Long userId) {
        User user = userMapper.selectById(userId);

        if (user == null) throw new RuntimeException("用户不存在");

        UserSimpleInfoVO vo = new UserSimpleInfoVO();
        BeanUtils.copyProperties(this, vo);
        vo.setUserId(userId);

        return vo;
    }

    @Override
    public UserDTO getUserDTO(Long userId) {
        User user = userMapper.selectById(userId);

        if (user == null) throw new RuntimeException("用户不存在");

        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(this, dto);

        return dto;
    }
}
