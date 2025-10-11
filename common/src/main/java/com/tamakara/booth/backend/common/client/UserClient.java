package com.tamakara.booth.backend.common.client;

import com.tamakara.booth.backend.common.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(value = "user-service", configuration = FeignConfig.class)
public interface UserClient {

}
