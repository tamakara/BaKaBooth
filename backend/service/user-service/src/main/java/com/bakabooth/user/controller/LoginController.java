package com.bakabooth.user.controller;

import com.bakabooth.user.domain.dto.LoginFormDTO;
import com.bakabooth.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "登录接口")
@RestController
@RequiredArgsConstructor()
public class LoginController {
    private final UserService userService;

    @Operation(summary = "登录")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginFormDTO loginFormDTO) {
        String token = userService.login(loginFormDTO);
        return ResponseEntity.ok(token);
    }
}
