package com.bakabooth.common.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "file-service")
public interface FileClient {
    @GetMapping("/url")
    ResponseEntity<String> getFileURL(
            @RequestParam("fileId") Long fileId
    );
}
