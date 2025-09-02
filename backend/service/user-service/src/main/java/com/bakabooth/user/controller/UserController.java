package com.bakabooth.user.controller;

import com.bakabooth.common.domain.dto.UserDTO;
import com.bakabooth.user.domain.dto.LoginFormDTO;
import com.bakabooth.user.domain.dto.RegisterFormDTO;
import com.bakabooth.user.domain.vo.UserSimpleInfoVO;
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

    @Operation(summary = "登录")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterFormDTO registerFormDTO) {
        String token = userService.register(registerFormDTO);
        return ResponseEntity.ok(token);
    }


    @Operation(summary = "获取简要用户信息")
    @GetMapping("/vo/simple-info")
    public ResponseEntity<UserSimpleInfoVO> getUserSimpleInfoVO(@RequestHeader("X-USER-ID") Long userId) {
        UserSimpleInfoVO userSimpleInfoVO = userService.getUserSimpleInfoVO(userId);
        return ResponseEntity.ok(userSimpleInfoVO);
    }

    @Operation(summary = "获取用户DTO")
    @GetMapping("/dto/user")
    public ResponseEntity<UserDTO> getUserDTO(@RequestParam Long userId) {
        UserDTO userDTO = userService.getUserDTO(userId);
        return ResponseEntity.ok(userDTO);
    }
}