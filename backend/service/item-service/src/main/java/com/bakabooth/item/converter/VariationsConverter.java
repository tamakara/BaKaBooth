package com.bakabooth.item.converter;

import com.bakabooth.item.domain.entity.Variation;
import com.bakabooth.item.domain.vo.VariationEditFormVO;
import com.bakabooth.item.domain.vo.VariationVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;


@Component
public class VariationsConverter {

    public VariationEditFormVO toVariationsEditFormVO(Variation variation) {
        VariationEditFormVO vo = new VariationEditFormVO();
        BeanUtils.copyProperties(variation, vo);
        return vo;
    }

    public VariationVO toVariationVO(Variation variation) {
        VariationVO vo = new VariationVO();
        BeanUtils.copyProperties(variation, vo);
        return vo;
    }
}
