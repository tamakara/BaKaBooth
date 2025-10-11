package com.tamakara.booth.backend.service.file.mapper;

import com.tamakara.booth.backend.service.file.domain.entity.File;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface FileMapper extends BaseMapper<File> {
    default boolean existsByHash(String hash) {
        return exists(new LambdaQueryWrapper<File>()
                .eq(File::getHash, hash)
        );
    }
}
