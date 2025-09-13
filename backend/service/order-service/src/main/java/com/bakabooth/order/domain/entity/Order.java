package com.bakabooth.order.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.Instant;

@TableName("order")
@Data
public class Order {
    @TableId
    private Long id;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "item_id")
    private Long itemId;

    @TableField(value = "item_price")
    private Long itemPrice;

    @TableField(value = "quantity")
    private Long quantity;

    // 0 - 已取消, 1 - 待付款, 2 - 待发货, 3 - 待收货, 4 - 已完成, 5 - 退货中, 6 - 退款中
    @TableField(value = "state_code")
    private Integer stateCode;

    @TableField(value = "payment_method")
    private Long paymentMethod;

    @TableField(value = "payment_amount")
    private Double paymentAmount;

    @TableField(value = "delivery_method")
    private Integer deliveryMethod;

    @TableField(value = "created_at")
    private Instant createdAt;

    public Order(Long userId, Long itemId, Long quantity) {
        this.userId = userId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.stateCode = 1;
    }
}
