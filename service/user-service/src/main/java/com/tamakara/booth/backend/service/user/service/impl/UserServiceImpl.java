package com.tamakara.booth.backend.service.user.service.impl;

import com.tamakara.booth.backend.common.client.FileClient;
import com.tamakara.booth.backend.common.util.JWTUtil;
import com.tamakara.booth.backend.service.user.domain.dto.LoginFormDTO;
import com.tamakara.booth.backend.service.user.domain.dto.RegisterFormDTO;
import com.tamakara.booth.backend.service.user.domain.entity.User;
import com.tamakara.booth.backend.service.user.domain.vo.UserVO;
import com.tamakara.booth.backend.service.user.mapper.UserMapper;
import com.tamakara.booth.backend.service.user.service.UserService;
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
            vo.setBalance(null);
            vo.setCreatedAt(null);
        }

        return vo;
    }

    @Override
    @Transactional
    public Boolean pay(Long userId, Long sellerId, Double amount) {
        User user = userMapper.selectById(userId);
        User seller = userMapper.selectById(sellerId);
        if (user == null || seller == null) {
            throw new RuntimeException("用户不存在");
        }

        if (user.getBalance() < amount) {
            throw new RuntimeException("余额不足");
        }

        user.setBalance(user.getBalance() - amount);
        seller.setBalance(seller.getBalance() + amount);
        userMapper.updateById(user);
        userMapper.updateById(seller);

        return true;
    }
}
