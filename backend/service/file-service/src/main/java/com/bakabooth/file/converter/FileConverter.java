package com.bakabooth.file.converter;

import com.bakabooth.file.domain.entity.File;
import com.bakabooth.file.domain.vo.FileVO;
import com.bakabooth.file.util.MinIOUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileConverter {
    private final MinIOUtil minIOUtil;

    public FileVO toFileVO(File file) {
        FileVO vo = new FileVO();
        BeanUtils.copyProperties(file, vo);
        vo.setUrl(minIOUtil.generateFileUrl(file.getHash(), 86400));
        return vo;
    }
}
