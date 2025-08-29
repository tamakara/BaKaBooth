package com.bakabooth.item.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tag")
public class Tag {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "item_id")
    private Long itemId;

    @TableField(value = "name")
    private String name;

    @TableField(value = "order_index")
    private Integer orderIndex;
}
