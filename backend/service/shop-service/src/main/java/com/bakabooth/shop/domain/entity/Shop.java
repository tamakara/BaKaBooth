package com.bakabooth.shop.domain.entity;

import com.bakabooth.shop.domain.vo.ShopInfoVO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.Instant;

@Data
@TableName("shop")
public class Shop {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "state")
    private String state;

    @TableField(value = "name")
    private String name;

    @TableField(value = "description")
    private String description;

    @TableField(value = "follower")
    private Long follower;

    public Shop(Long userId) {
        this.userId = userId;
        this.state = "private";
        this.name = "未命名";
        this.description = "请设置店铺描述";
    }

    public ShopInfoVO toShopInfoVO() {
        ShopInfoVO vo = new ShopInfoVO();

        vo.setShopId(this.id);
        vo.setName(this.name);
        vo.setDescription(this.description);
        vo.setFollower(this.follower);
        vo.setState(this.state);

        return vo;
    }
}
