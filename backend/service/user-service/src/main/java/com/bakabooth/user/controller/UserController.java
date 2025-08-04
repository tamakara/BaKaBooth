package com.bakabooth.user.controller;

import com.bakabooth.user.domain.vo.UserSimpleInfoVO;
import com.bakabooth.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "获取简要用户信息")
    @GetMapping("/simple-info")
    public ResponseEntity<UserSimpleInfoVO> getUserSimpleInfo(@RequestHeader("X-UID") Long userId) {
        UserSimpleInfoVO userSimpleInfoVO = userService.getUserSimpleInfo(userId);
        return ResponseEntity.ok(userSimpleInfoVO);
    }
}