package com.bakabooth.item.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("file")
public class File {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "variation_id")
    private Long variationId;

    @TableField(value = "order_index")
    private Integer orderIndex;

    @TableField(value = "order_index")
    private String hash;
}
