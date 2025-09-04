package com.bakabooth.item.controller;

import com.bakabooth.item.domain.vo.ItemInfoVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "商品接口")
@RestController
@RequiredArgsConstructor
public class ItemController {
//    @Operation(summary = "获取商品信息")
//    @GetMapping("/vo/item-info")
//    public ResponseEntity<ItemInfoVO> getItemInfoVO(
//            @RequestHeader("X-USER-ID") Long userId
//    ) {
//
//        return ResponseEntity.ok();
//    }


}
