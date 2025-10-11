package com.tamakara.booth.backend.service.file.service.Impl;

import com.tamakara.booth.backend.service.file.domain.entity.File;
import com.tamakara.booth.backend.service.file.mapper.FileMapper;
import com.tamakara.booth.backend.service.file.service.FileService;
import com.tamakara.booth.backend.service.file.util.HashUtil;
import com.tamakara.booth.backend.service.file.util.MinIOUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final MinIOUtil minIOUtil;
    private final FileMapper fileMapper;

    @Transactional
    public Long upload( MultipartFile multipartFile) {
        File file = new File();
        file.setName(multipartFile.getOriginalFilename());
        file.setHash(HashUtil.calculateHash(multipartFile));
        file.setSize(multipartFile.getSize());

        if (!fileMapper.existsByHash(file.getHash()))
            minIOUtil.upload(file.getHash(), multipartFile);

        fileMapper.insert(file);

        return file.getId();
    }

    public String getFileURL(Long fileId, Integer time) {
        File file = fileMapper.selectById(fileId);
        if (file == null) throw new RuntimeException("文件不存在");
        return minIOUtil.generateFileUrl(file.getHash(), time);
    }
}
