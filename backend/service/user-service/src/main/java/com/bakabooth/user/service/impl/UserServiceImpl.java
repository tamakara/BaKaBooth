package com.bakabooth.user.service.impl;

import com.bakabooth.user.domain.entity.User;
import com.bakabooth.user.service.IUserService;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<BaseMapper<User>, User> implements IUserService {


}
