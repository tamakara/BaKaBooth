package com.bakabooth.order.domain.entity;

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

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "item_id")
    private Long itemId;

    // 0 - 已取消, 1 - 待付款, 2 - 待发货, 3 - 待收货, 4 - 已完成, 5 - 退货中, 6 - 退款中
    @TableField(value = "state_code")
    private Integer stateCode;

    @TableField(value = "pay_amount")
    private Double payAmount;

    @TableField(value = "created_at")
    private Instant createdAt;

    @TableField(value = "pay_at")
    private Instant payAt;

    @TableField(value = "delivery_at")
    private Instant deliveryAt;

    @TableField(value = "receive_at")
    private Instant receiveAt;

    @TableField(value = "finish_at")
    private Instant finishAt;

    @TableField(value = "return_request_at")
    private Instant returnRequestAt;

    @TableField(value = "return_at")
    private Instant returnAt;

    @TableField(value = "refund_at")
    private Instant refundAt;

    @TableField(value = "return_finish_at")
    private Instant returnFinishAt;
}
