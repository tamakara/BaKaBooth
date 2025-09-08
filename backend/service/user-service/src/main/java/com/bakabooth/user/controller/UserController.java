package com.bakabooth.user.controller;

import com.bakabooth.user.domain.dto.LoginFormDTO;
import com.bakabooth.user.domain.dto.RegisterFormDTO;
import com.bakabooth.user.domain.vo.SellerVO;
import com.bakabooth.user.domain.vo.ShopManageVO;
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

    @Operation(summary = "注册")
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

    @Operation(summary = "获取店铺管理信息")
    @GetMapping("/vo/shop-manage-page")
    public ResponseEntity<ShopManageVO> getShopManageVO(
            @RequestHeader("X-USER-ID") Long userId
    ) {
        ShopManageVO vo = userService.getShopManageVO(userId);
        return ResponseEntity.ok(vo);
    }

    @Operation(summary = "获取卖家信息")
    @GetMapping("/user/vo/seller-user/{sellerUserId}")
    public ResponseEntity<SellerVO> getSellerUserVO(@PathVariable("sellerUserId") Long sellerUserId) {
        SellerVO sellerUserVO = userService.getSellerUserVO(sellerUserId);
        return ResponseEntity.ok(sellerUserVO);
    }
}