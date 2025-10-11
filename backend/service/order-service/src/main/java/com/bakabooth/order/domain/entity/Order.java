package com.bakabooth.order.domain.entity;

import com.bakabooth.order.domain.pojo.OrderState;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.Instant;

@TableName("`order`")
@Data
public class Order {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "item_id")
    private Long itemId;

    @TableField(value = "seller_id")
    private Long sellerId;

    @TableField(value = "order_state")
    private OrderState orderState;

    @TableField(value = "pay_amount")
    private Double payAmount;

    @TableField(value = "created_at")
    private Instant createdAt;

    @TableField(value = "pay_at")
    private Instant payAt;

    @TableField(value = "shipped_at")
    private Instant shippedAt;

    @TableField(value = "completed_at")
    private Instant completedAt;

    @TableField(value = "refund_request_at")
    private Instant refundRequestAt;

    @TableField(value = "refund_at")
    private Instant refundAt;

    @TableField(value = "cancelled_at")
    private Instant cancelledAt;
}
