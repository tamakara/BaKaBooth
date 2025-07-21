package com.bakabooth.user.controller;

import com.bakabooth.user.domain.dto.LoginFormDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "登录接口", description = "提供用户登录相关操作的接口")
@RestController
@RequestMapping("/login")
public class LoginController {

    @Operation(summary = "登录")
    @PostMapping()
    public void login(@RequestBody LoginFormDTO loginFormDTO) {

    }

}
