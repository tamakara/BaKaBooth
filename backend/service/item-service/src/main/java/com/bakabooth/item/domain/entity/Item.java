package com.bakabooth.item.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@TableName("item")
public class Item {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "shop_id")
    private Long shopId;

    @TableField(value = "state")
    private String state;

    @TableField(value = "name")
    private String name;

    @TableField(value = "description")
    private String description;

    @TableField(value = "favorite")
    private Long favorite;

    public Item(Long shopId) {
        this.shopId = shopId;
        this.state = "private";
        this.name = "";
        this.description = "";
        this.favorite = 0L;
    }
}
