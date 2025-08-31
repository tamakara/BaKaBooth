package com.bakabooth.item.mapper;

import com.bakabooth.item.domain.entity.File;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FileMapper extends BaseMapper<File> {

    @Transactional
    default void updateFiles(Long variationId, List<Long> fileIds) {
        delete(new LambdaQueryWrapper<File>().eq(File::getVariationId, variationId));
        for (int index = 0; index < fileIds.size(); index++) {
            File file = new File();
            file.setVariationId(variationId);
            file.setFileId(fileIds.get(index));
            file.setOrderIndex(index);
            insert(file);
        }
    }
}
