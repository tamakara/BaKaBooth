package com.bakabooth.file.controller;

import com.bakabooth.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<Long> uploadFile(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestParam("file") MultipartFile file
    ) {
        Long fileId = fileService.uploadFile(userId, file);
        return ResponseEntity.ok(fileId);
    }

    @GetMapping("/url")
    public ResponseEntity<String> getFileUrl(
            @RequestParam("fileId") Long fileId,
            @RequestParam(name = "time", defaultValue = "86400") Integer time
    ) {
        String url = fileService.getFileVO(fileId, time);
        return ResponseEntity.ok(url);
    }
}
