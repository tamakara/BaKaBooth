package com.bakabooth.file.controller;

import com.bakabooth.file.domain.vo.FileVO;
import com.bakabooth.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<FileVO> upload(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(name = "isPublic", defaultValue = "false") Boolean isPublic
    ) {
        FileVO vo = fileService.saveFile(userId, file,  isPublic);
        return ResponseEntity.ok(vo);
    }

    @PostMapping("/vo/file")
    public ResponseEntity<FileVO> getFileUrl(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestParam("fileId") Long fileId
    ) {
        FileVO vo = fileService.getFileVO(userId, fileId);
        return ResponseEntity.ok(vo);
    }
}
