package com.tamakara.booth.backend.service.file.service;

import org.springframework.web.multipart.MultipartFile;


public interface FileService {

    Long upload( MultipartFile multipartFile);

    String getFileURL(Long fileId, Integer time);
}
