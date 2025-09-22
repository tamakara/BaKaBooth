package com.bakabooth.common.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "item-service")
public interface ItemClient {

}
