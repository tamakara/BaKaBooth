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

    default void updateTags(Long itemId, List<String> tags) {
        delete(new LambdaQueryWrapper<Tag>().eq(Tag::getItemId, itemId));
        for (int index = 0; index < tags.size(); index++) {
            Tag tag = new Tag();
            tag.setItemId(itemId);
            tag.setName(tags.get(index));
            tag.setOrderIndex(index);
            insert(tag);
        }
    }
}
