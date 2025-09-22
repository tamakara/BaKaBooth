package com.bakabooth.order.domain.entity;

import com.bakabooth.order.domain.vo.OrderEditFormVO;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.Instant;

@TableName("order")
@Data
public class Order {
    @TableId
    private Long id;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "variation_id")
    private Long variationId;

    @TableField(value = "quantity")
    private Long quantity;

    // 0 - 已取消, 1 - 待付款, 2 - 待发货, 3 - 待收货, 4 - 已完成, 5 - 退货中, 6 - 退款中
    @TableField(value = "state_code")
    private Integer stateCode;

    @TableField(value = "pay_id")
    private Long payId;

    @TableField(value = "pay_method")
    private Long payMethod;

    @TableField(value = "pay_amount")
    private Double payAmount;

    @TableField(value = "created_at")
    private Instant createdAt;

    public Order(Long userId, OrderEditFormVO orderEditFormVO) {
        this.stateCode = 1;
        this.userId = userId;
        BeanUtils.copyProperties(orderEditFormVO, this);
    }

}
