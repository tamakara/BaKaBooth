package com.tamakara.booth.backend.common.client;

import com.tamakara.booth.backend.common.config.FeignConfig;
import com.tamakara.booth.backend.common.domain.vo.ItemVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "item-service", configuration = FeignConfig.class)
public interface ItemClient {

    @PutMapping("/lock/{itemId}")
    ResponseEntity<Boolean> lockItem(
            @PathVariable("itemId") Long itemId
    );

    @GetMapping("/vo/item/{itemId}")
    ResponseEntity<ItemVO> getItemVO(
            @PathVariable("itemId") Long itemId
    );

}
