package com.bakabooth.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "shop-service")
public interface ShopClient {



}
