package com.bakabooth.user.controller;

import com.bakabooth.common.domain.dto.UserDTO;
import com.bakabooth.user.domain.vo.UserSimpleInfoVO;
import com.bakabooth.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "获取简要用户信息")
    @GetMapping("/vo/simple-info")
    public ResponseEntity<UserSimpleInfoVO> getUserSimpleInfoVO(@RequestHeader("X-UID") Long userId) {
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