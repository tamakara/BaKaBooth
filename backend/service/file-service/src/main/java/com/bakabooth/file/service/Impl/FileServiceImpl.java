package com.bakabooth.file.service.Impl;

import com.bakabooth.common.domain.dto.FileDTO;
import com.bakabooth.file.converter.FileConverter;
import com.bakabooth.file.domain.entity.File;
import com.bakabooth.file.domain.entity.Permission;
import com.bakabooth.file.mapper.FileMapper;
import com.bakabooth.file.mapper.PermissionMapper;
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
    private final PermissionMapper permissionMapper;
    private final FileConverter fileConverter;

    @Transactional
    public FileDTO saveFile(Long userId, MultipartFile multipartFile, Boolean isPublic) {
        File file = new File();

        file.setUserId(userId);
        file.setName(multipartFile.getOriginalFilename());
        file.setHash(HashUtil.calculateHash(multipartFile));
        file.setSize(multipartFile.getSize());
        file.setIsPublic(isPublic);

        if (!fileMapper.existsByHash(file.getHash()))
            minIOUtil.upload(file.getHash(), multipartFile);

        fileMapper.insert(file);

        Permission permission = new Permission();
        permission.setUserId(userId);
        permission.setFileId(file.getId());

        permissionMapper.insert(permission);

        return fileConverter.toFileVO(file);
    }

    public FileDTO getFileVO(Long userId, Long fileId) {
        File file = fileMapper.selectById(fileId);
        if (file == null) throw new RuntimeException("文件不存在");
        if (!file.getIsPublic() && permissionMapper.existsByFileIdAndUserId(fileId, userId) == null)
            throw new RuntimeException("权限不足");
        return fileConverter.toFileVO(file);
    }
}
