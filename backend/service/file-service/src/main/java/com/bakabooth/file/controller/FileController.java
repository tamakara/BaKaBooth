package com.bakabooth.file.controller;

import com.bakabooth.file.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "文件接口")
@RestController
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @Operation(summary = "上传文件")
    @PostMapping("/upload")
    public ResponseEntity<Long> upload(
            @RequestHeader(name = "X-USER-ID", defaultValue = "0") Long userId,
            @RequestParam("file") MultipartFile file
    ) {
        Long fileId = fileService.upload(userId, file);
        return ResponseEntity.ok(fileId);
    }

    @Operation(summary = "获取文件URL")
    @GetMapping("/url")
    public ResponseEntity<String> getFileURL(
            @RequestParam("fileId") Long fileId,
            @RequestParam(name = "time", defaultValue = "86400") Integer time
    ) {
        String url = fileService.getFileURL(fileId, time);
        return ResponseEntity.ok(url);
    }
}
