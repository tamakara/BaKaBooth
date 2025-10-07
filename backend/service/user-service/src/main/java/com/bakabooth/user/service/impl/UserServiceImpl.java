package com.bakabooth.user.service.impl;

import com.bakabooth.common.client.FileClient;
import com.bakabooth.common.util.JWTUtil;
import com.bakabooth.user.domain.dto.LoginFormDTO;
import com.bakabooth.user.domain.dto.RegisterFormDTO;
import com.bakabooth.user.domain.entity.User;
import com.bakabooth.user.domain.vo.UserVO;
import com.bakabooth.user.mapper.UserMapper;
import com.bakabooth.user.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final UserMapper userMapper;
    private final FileClient fileClient;

    @Override
    @Transactional
    public String login(LoginFormDTO loginFormDTO) {
        if (loginFormDTO.getPhone() == null || loginFormDTO.getPassword() == null) {
            throw new RuntimeException("手机号或密码不能为空");
        }

        User user = userMapper.getUserByPhoneAndPassword(loginFormDTO.getPhone(), loginFormDTO.getPassword());

        if (user == null) throw new RuntimeException("手机号或密码错误");

        return JWTUtil.generateJWT(user.getId(), 3600 * 24 * 7);
    }

    @Override
    @Transactional
    public String register(RegisterFormDTO registerFormDTO) {
        if (registerFormDTO.getPhone() == null || registerFormDTO.getPassword() == null) {
            throw new RuntimeException("手机号或密码或或用户名不能为空");
        }

        User user = new User();
        userMapper.insert(user);
        user.setUsername("User" + user.getId());
        BeanUtils.copyProperties(registerFormDTO, user);
        updateById(user);

        return JWTUtil.generateJWT(user.getId(), 3600 * 24 * 7);
    }

    @Override
    @Transactional(readOnly = true)
    public UserVO getUserVO(Long userId, Long sellerId) {
        User user = userMapper.selectById(sellerId);
        if (user == null) throw new RuntimeException("用户不存在");

        UserVO vo = new UserVO();
        BeanUtils.copyProperties(user, vo);

        String avatarUrl = fileClient.getFileURL(user.getAvatarFileId()).getBody();
        vo.setAvatarUrl(avatarUrl);

        vo.setIsCurrentUser(sellerId.equals(userId));
        if (!vo.getIsCurrentUser()) {
            vo.setPhone(null);
            vo.setCreatedAt(null);
        }

        return vo;
    }
}
