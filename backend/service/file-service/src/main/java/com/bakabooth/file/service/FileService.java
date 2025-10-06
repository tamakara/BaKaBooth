package com.bakabooth.file.service;

import org.springframework.web.multipart.MultipartFile;


public interface FileService {

    Long upload(Long userId, MultipartFile multipartFile);

    String getFileURL(Long fileId, Integer time);
}
