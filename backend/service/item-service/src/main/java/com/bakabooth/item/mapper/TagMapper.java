package com.bakabooth.item.mapper;

import com.bakabooth.item.domain.entity.Tag;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface TagMapper extends BaseMapper<Tag> {

    default List<Tag> selectTagsByItemId(Long itemId) {
        return selectList(
                new LambdaQueryWrapper<Tag>()
                        .eq(Tag::getItemId, itemId)
        );
    }
}
