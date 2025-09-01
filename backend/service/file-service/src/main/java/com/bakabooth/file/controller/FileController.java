package com.bakabooth.file.controller;

import com.bakabooth.common.domain.dto.FileDTO;
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
    public ResponseEntity<FileDTO> upload(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(name = "isPublic", defaultValue = "false") Boolean isPublic
    ) {
        FileDTO vo = fileService.saveFile(userId, file,  isPublic);
        return ResponseEntity.ok(vo);
    }

    @GetMapping("/vo/file")
    public ResponseEntity<FileDTO> getFileUrl(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestParam("fileId") Long fileId
    ) {
        FileDTO vo = fileService.getFileVO(userId, fileId);
        return ResponseEntity.ok(vo);
    }
}
