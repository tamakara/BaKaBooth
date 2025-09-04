package com.bakabooth.user.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bakabooth.common.client.FileClient;
import com.bakabooth.common.domain.dto.FileDTO;
import com.bakabooth.common.domain.dto.UserDTO;
import com.bakabooth.user.config.JwtProperties;
import com.bakabooth.user.domain.dto.LoginFormDTO;
import com.bakabooth.user.domain.dto.RegisterFormDTO;
import com.bakabooth.user.domain.entity.Shop;
import com.bakabooth.user.domain.entity.User;
import com.bakabooth.user.domain.vo.UserSimpleInfoVO;
import com.bakabooth.user.mapper.ShopMapper;
import com.bakabooth.user.mapper.UserMapper;
import com.bakabooth.user.service.ShopService;
import com.bakabooth.user.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
    private final ShopMapper shopMapper;
    private final FileClient fileClient;

    @Override
    @Transactional
    public String login(LoginFormDTO loginFormDTO) {
        if (loginFormDTO.getPhone() == null || loginFormDTO.getPassword() == null) {
            throw new RuntimeException("手机号或密码不能为空");
        }

        User user = userMapper.getUserByPhoneAndPassword(
                loginFormDTO.getPhone(),
                loginFormDTO.getPassword()
        );

        if (user == null) throw new RuntimeException("手机号或密码错误");

        String userId = user.getId().toString();
        String jti = UUID.randomUUID().toString();
        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecret());

        return JWT.create()
                .withExpiresAt(
                        Instant.now()
                                .plusSeconds(jwtProperties.getExpiration())
                )
                .withSubject(userId)
                .withClaim("jti", jti)
                .sign(algorithm);
    }

    @Override
    @Transactional
    public String register(RegisterFormDTO registerFormDTO) {
        if (registerFormDTO.getPhone() == null || registerFormDTO.getPassword() == null || registerFormDTO.getNickname() == null) {
            throw new RuntimeException("手机号或密码或或用户昵称不能为空");
        }

        User user = new User();
        user.setPhone(registerFormDTO.getPhone());
        user.setUsername(registerFormDTO.getNickname());
        user.setPassword(registerFormDTO.getPassword());
        user.setNickname(registerFormDTO.getNickname());
        user.setAvatarFileId(0L);
        userMapper.insert(user);

        Long userId = user.getId();

        Shop shop = new Shop(userId);
        shopMapper.insert(shop);

        String jti = UUID.randomUUID().toString();
        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecret());

        return JWT.create()
                .withExpiresAt(Instant
                        .now()
                        .plusSeconds(jwtProperties.getExpiration())
                )
                .withSubject(userId.toString())
                .withClaim("jti", jti)
                .sign(algorithm);
    }


    @Override
    public UserSimpleInfoVO getUserSimpleInfoVO(Long userId) {
        User user = userMapper.selectById(userId);

        if (user == null) throw new RuntimeException("用户不存在");

        UserSimpleInfoVO vo = new UserSimpleInfoVO();
        BeanUtils.copyProperties(user, vo);
        if (user.getAvatarFileId() != 0) {
            FileDTO avatar = fileClient.getFileUrl(userId, user.getAvatarFileId()).getBody();
            if (avatar == null) throw new RuntimeException("头像文件不存在");
            vo.setAvatarUrl(avatar.getUrl());
        }

        return vo;
    }

    @Override
    public UserDTO getUserDTO(Long userId) {
        User user = userMapper.selectById(userId);

        if (user == null) throw new RuntimeException("用户不存在");

        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(user, dto);

        return dto;
    }
}
