package com.bakabooth.common.client;

import com.bakabooth.common.domain.dto.FileDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "file-service")
public interface FileClient {
    @GetMapping("/vo/file")
    ResponseEntity<FileDTO> getFileUrl(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestParam("fileId") Long fileId
    );
}
