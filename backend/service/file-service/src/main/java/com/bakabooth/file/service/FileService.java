package com.bakabooth.file.service;

import com.bakabooth.common.domain.dto.FileDTO;
import org.springframework.web.multipart.MultipartFile;


public interface FileService {

    FileDTO saveFile(Long userId, MultipartFile multipartFile, Boolean isPublic);

    FileDTO getFileVO(Long userId, Long fileId);
}
