package com.bakabooth.common.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "shop-service")
public interface ShopClient {
    @PostMapping("/shop/create")
    ResponseEntity<Long> create(@RequestBody Long userId);
}
