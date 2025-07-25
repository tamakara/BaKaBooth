package com.bakabooth.user.controller;

import com.bakabooth.user.domain.dto.LoginFormDTO;
import com.bakabooth.user.domain.vo.UserLoginVO;
import com.bakabooth.user.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "登录接口", description = "提供用户登录相关操作的接口")
@RestController
@RequiredArgsConstructor()
public class LoginController {

    private final UserServiceImpl userService;

    @Operation(summary = "登录")
    @PostMapping("/login")
    public ResponseEntity<UserLoginVO> login(@RequestBody LoginFormDTO loginFormDTO) {
        UserLoginVO userLoginVO = userService.login(loginFormDTO);
        return ResponseEntity.ok().body(userLoginVO);
    }

}
