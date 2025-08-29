package com.bakabooth.item.mapper;

import com.bakabooth.item.domain.entity.File;
import com.bakabooth.item.domain.vo.VariationsEditFormVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FileMapper extends BaseMapper<File> {

    @Transactional
    default void updateFiles(List<VariationsEditFormVO> variations) {

    }
}
