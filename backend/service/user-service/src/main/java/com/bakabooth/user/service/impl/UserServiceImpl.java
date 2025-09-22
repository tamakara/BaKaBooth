package com.bakabooth.user.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bakabooth.common.client.FileClient;
import com.bakabooth.user.config.JwtProperties;
import com.bakabooth.user.domain.dto.LoginFormDTO;
import com.bakabooth.user.domain.dto.RegisterFormDTO;
import com.bakabooth.user.domain.entity.User;
import com.bakabooth.user.domain.vo.UserVO;
import com.bakabooth.user.mapper.UserMapper;
import com.bakabooth.user.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final UserMapper userMapper;
    private final FileClient fileClient;
    private final JwtProperties jwtProperties;

    @Override
    @Transactional
    public String login(LoginFormDTO loginFormDTO) {
        if (loginFormDTO.getPhone() == null || loginFormDTO.getPassword() == null) {
            throw new RuntimeException("手机号或密码不能为空");
        }

        User user = userMapper.getUserByPhoneAndPassword(loginFormDTO.getPhone(), loginFormDTO.getPassword());

        if (user == null) throw new RuntimeException("手机号或密码错误");

        String userId = user.getId().toString();
        String jti = UUID.randomUUID().toString();
        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecret());

        return JWT.create().withExpiresAt(Instant.now().plusSeconds(jwtProperties.getExpiration())).withSubject(userId).withClaim("jti", jti).sign(algorithm);
    }

    @Override
    @Transactional
    public String register(RegisterFormDTO registerFormDTO) {
        if (registerFormDTO.getPhone() == null || registerFormDTO.getPassword() == null) {
            throw new RuntimeException("手机号或密码或或用户名不能为空");
        }

        User user = new User(
                registerFormDTO.getPhone(),
                registerFormDTO.getPassword()
        );
        userMapper.insert(user);

        Long userId = user.getId();
        lambdaUpdate()
                .eq(User::getId, userId)
                .set(User::getUsername, "用户" + userId);

        String jti = UUID.randomUUID().toString();
        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecret());

        return JWT.create().withExpiresAt(Instant.now().plusSeconds(jwtProperties.getExpiration())).withSubject(userId.toString()).withClaim("jti", jti).sign(algorithm);
    }

    @Override
    public UserVO getUserVO(Long userId, Long sellerId, Integer modeCode) {
        User user = userMapper.selectById(sellerId);
        if (modeCode == 2 && !userId.equals(sellerId)) {
            throw new RuntimeException("没有权限");
        }
        String avatarUrl = fileClient.getFileUrl(user.getAvatarFileId()).getBody();
        return User.toUserVO(user, modeCode, avatarUrl);
    }
}
