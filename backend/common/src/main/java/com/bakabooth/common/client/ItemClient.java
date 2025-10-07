package com.bakabooth.common.client;

import com.bakabooth.common.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "item-service", configuration = FeignConfig.class)
public interface ItemClient {

    @PutMapping("/lock/{itemId}")
    ResponseEntity<Boolean> lockItem(
            @PathVariable("itemId") Long itemId
    );

}
