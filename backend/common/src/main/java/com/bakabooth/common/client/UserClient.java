package com.bakabooth.common.client;

import com.bakabooth.common.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(value = "user-service", configuration = FeignConfig.class)
public interface UserClient {

}
