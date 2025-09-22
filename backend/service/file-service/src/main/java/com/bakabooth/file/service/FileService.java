package com.bakabooth.file.service;

import org.springframework.web.multipart.MultipartFile;


public interface FileService {

    Long uploadFile(Long userId, MultipartFile multipartFile);

    String getFileVO( Long fileId,Integer time);
}
