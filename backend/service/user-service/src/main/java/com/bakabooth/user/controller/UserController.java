package com.bakabooth.user.controller;

import com.bakabooth.user.domain.dto.LoginFormDTO;
import com.bakabooth.user.domain.dto.RegisterFormDTO;
import com.bakabooth.user.domain.vo.UserVO;
import com.bakabooth.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户接口")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "登录")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginFormDTO loginFormDTO) {
        String token = userService.login(loginFormDTO);
        return ResponseEntity.ok(token);
    }

    @Operation(summary = "注册")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterFormDTO registerFormDTO) {
        String token = userService.register(registerFormDTO);
        return ResponseEntity.ok(token);
    }

    @Operation(summary = "获取用户信息")
    @GetMapping("/vo/user")
    public ResponseEntity<UserVO> getUserVO(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestParam(name = "sellerId", required = false) Long sellerId
    ) {
        if (sellerId == null) sellerId = userId;
        UserVO userVO = userService.getUserVO(userId, sellerId);
        return ResponseEntity.ok(userVO);
    }
}