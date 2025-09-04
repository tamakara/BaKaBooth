package com.bakabooth.item.converter;

import com.bakabooth.item.domain.entity.File;
import com.bakabooth.item.domain.entity.Variation;
import com.bakabooth.item.domain.vo.VariationEditFormVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VariationsConverter {

    public VariationEditFormVO toVariationsEditFormVO(Variation variation, List<File> files) {
        VariationEditFormVO vo = new VariationEditFormVO();
        BeanUtils.copyProperties(variation, vo);
        vo.setFiles(files.stream().map(File::getFileId).toList());
        return vo;
    }
}
