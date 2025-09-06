package com.bakabooth.item.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@TableName("variation")
@NoArgsConstructor
public class Variation {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "item_id")
    private Long itemId;

    @TableField(value = "order_index")
    private Integer orderIndex;

    @TableField(value = "name")
    private String name;

    @TableField(value = "type")
    private String type;

    @TableField(value = "price")
    private Double price;

    @TableField(value = "stock")
    private Long stock;

    public Variation(Long itemId) {
        this.itemId = itemId;
        this.orderIndex = 0;
        this.name = "默认";
        this.type = "digital";
        this.price = 0.0;
        this.stock = 0L;
    }
}