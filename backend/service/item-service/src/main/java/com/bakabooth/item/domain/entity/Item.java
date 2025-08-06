package com.bakabooth.item.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@TableName("item")
public class Item {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "name")
    private String name;

    @TableField(value = "state")
    private String state;

    @TableField(value = "favorite")
    private Long favorite;

    @TableField(value = "cover_file")
    private String coverFile;

    @TableField(value = "shop_id")
    private Long shopId;

    @TableField(value = "variations", typeHandler = JacksonTypeHandler.class)
    private List<Variation> variations;

    @TableField(value = "created_at")
    private Instant createdAt;

    @TableField(value = "updated_at")
    private Instant updatedAt;

    public Item(Long shopId) {
        this.name = "未命名";
        this.state = "draft";
        this.favorite = 0L;
        this.shopId = shopId;
        this.variations = new ArrayList<>();
        variations.add(new Variation("默认", 0.00, 0L, 0L));
    }
}
