package com.tamakara.booth.backend.service.item.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@TableName("image")
public class Image {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "item_id")
    private Long itemId;

    @TableField(value = "file_id")
    private Long fileId;

    @TableField(value = "order_index")
    private Integer orderIndex;
}
