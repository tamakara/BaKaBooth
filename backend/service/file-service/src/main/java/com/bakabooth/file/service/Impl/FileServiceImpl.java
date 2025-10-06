package com.bakabooth.file.service.Impl;

import com.bakabooth.file.domain.entity.File;
import com.bakabooth.file.mapper.FileMapper;
import com.bakabooth.file.service.FileService;
import com.bakabooth.file.util.HashUtil;
import com.bakabooth.file.util.MinIOUtil;
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
    public Long upload(Long userId, MultipartFile multipartFile) {
        File file = new File();
        file.setUserId(userId);
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
