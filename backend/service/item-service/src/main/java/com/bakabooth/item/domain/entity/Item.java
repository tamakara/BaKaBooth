package com.bakabooth.item.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.Instant;

@Data
@TableName("item")
public class Item {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "user_id")
    private Long userId;

    // 0-已删除, 1-草稿, 2-在售, 3-已下架, 4-锁定, 5-已售出
    @TableField(value = "state_code")
    private Integer stateCode;

    // 0-无需邮寄, 1-包邮, 2-固定邮费
    @TableField(value = "delivery_method_code")
    private Integer deliveryMethodCode;

    @TableField(value = "price")
    private Double price;

    @TableField(value = "postage")
    private Double postage;

    @TableField(value = "name")
    private String name;

    @TableField(value = "description")
    private String description;

    @TableField(value = "favorites")
    private Long favorites;

    @TableField(value = "created_at")
    private Instant createdAt;

    @TableField(value = "updated_at")
    private Instant updatedAt;
}
