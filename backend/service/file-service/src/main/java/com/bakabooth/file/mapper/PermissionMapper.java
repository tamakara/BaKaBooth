package com.bakabooth.file.mapper;

import com.bakabooth.file.domain.entity.Permission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface PermissionMapper extends BaseMapper<Permission> {
    default Boolean existsByFileIdAndUserId(Long fileId, Long userId) {
        return exists(new LambdaQueryWrapper<Permission>()
                .eq(Permission::getFileId, fileId)
                .eq(Permission::getUserId, userId)
        );
    }
}
