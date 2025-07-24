package com.bakabooth.user.controller;

import com.bakabooth.user.domain.dto.LoginFormDTO;
import com.bakabooth.user.domain.vo.UserStatusVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "登录接口", description = "提供用户登录相关操作的接口")
@RestController
public class LoginController {

    @Operation(summary = "登录")
    @PostMapping("/login")
    public ResponseEntity<UserStatusVO> login(@RequestBody LoginFormDTO loginFormDTO) {
        UserStatusVO userStatusVO = new UserStatusVO();
        userStatusVO.setId(0L);
        userStatusVO.setToken("mock-token");
        userStatusVO.setUsername(loginFormDTO.getUsername());
        userStatusVO.setNickname("Mock User");
        userStatusVO.setAvatarFile("https://example.com/avatar.png");
        return ResponseEntity.ok().body(userStatusVO);
    }

}
