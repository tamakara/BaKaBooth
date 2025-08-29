package com.bakabooth.item.mapper;

import com.bakabooth.item.domain.entity.Variation;
import com.bakabooth.item.domain.vo.VariationsEditFormVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface VariationMapper extends BaseMapper<Variation> {

    default List<Variation> selectVariationsByItemId(Long itemId) {
        return selectList(
                new LambdaQueryWrapper<Variation>()
                        .eq(Variation::getItemId, itemId)
                        .orderByAsc(Variation::getOrderIndex)
        );
    }

    @Transactional
    default void updateVariations(Long itemId, List<VariationsEditFormVO> variations) {
        delete(new LambdaQueryWrapper<Variation>().eq(Variation::getItemId, itemId));
        for (int index = 0; index < variations.size(); index++) {
            VariationsEditFormVO vo = variations.get(index);

            int row = update(null, new LambdaUpdateWrapper<Variation>()
                    .eq(Variation::getId, vo.getId())
                    .set(Variation::getName, vo.getName())
                    .set(Variation::getPrice, vo.getPrice())
                    .set(Variation::getStock, vo.getStock())
            );

            if (row == 0) {
                Variation variation = new Variation();
                variation.setName(vo.getName());
                variation.setItemId(itemId);
                variation.setOrderIndex(index);
                variation.setPrice(vo.getPrice());
                variation.setStock(vo.getStock());
                variation.setSales(0L);
                insert(variation);
            }
        }
    }


}
