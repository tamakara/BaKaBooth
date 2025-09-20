package com.bakabooth.item.mapper;

import com.bakabooth.item.domain.entity.Item;
import com.bakabooth.item.domain.vo.ItemEditFormVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface ItemMapper extends BaseMapper<Item> {

}
