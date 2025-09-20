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

    // 0-快照, 1-草稿, 2-在售, 3-已下架, 4-已售罄
    @TableField(value = "state_code")
    private Integer stateCode;

    @TableField(value = "name")
    private String name;

    @TableField(value = "description")
    private String description;

    @TableField(value = "favorites")
    private Long favorites;

    @TableField(value = "delivery_time")
    private String deliveryTime;

    @TableField(value = "shipping_details")
    private String shippingDetails;

    @TableField(value = "return_policy")
    private String returnPolicy;

    @TableField(value = "warranty_period")
    private String warrantyPeriod;

    @TableField(value = "created_at")
    private Instant createdAt;

    public Item(Long userId) {
        this.userId = userId;
        this.stateCode = 1;
        this.name = "未命名";
        this.description = "";
        this.favorites = 0L;
        this.deliveryTime = "";
        this.shippingDetails = "";
        this.returnPolicy = "";
        this.warrantyPeriod = "";
        this.createdAt = Instant.now();
    }
}
