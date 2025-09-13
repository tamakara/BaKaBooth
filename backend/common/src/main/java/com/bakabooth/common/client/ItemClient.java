package com.bakabooth.common.client;

import com.bakabooth.common.domain.dto.FileDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "item-service")
public interface ItemClient {
    @GetMapping("/vo/item/{itemId}")
    ResponseEntity<FileDTO> getItemVO(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable("itemId") Long itemId
    );
}
