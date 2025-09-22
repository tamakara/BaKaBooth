package com.bakabooth.item.domain.entity;

import com.bakabooth.item.domain.vo.ItemEditFormVO;
import com.bakabooth.item.domain.vo.ItemVO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.Instant;
import java.util.List;

@Data
@TableName("item")
public class Item {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "user_id")
    private Long userId;

    // 0-已删除, 1-在售 ,2-已下架, 3-已售出
    @TableField(value = "state_code")
    private Integer stateCode;

    @TableField(value = "name")
    private String name;

    @TableField(value = "price")
    private Double price;

    @TableField(value = "description")
    private String description;

    @TableField(value = "postage")
    private Integer postage;

    @TableField(value = "return_period")
    private Integer returnPeriod;

    @TableField(value = "delivery_period")
    private Integer deliveryPeriod;

    @TableField(value = "favorites")
    private Long favorites;

    @TableField(value = "created_at")
    private Instant createdAt;

    @TableField(value = "updated_at")
    private Instant updatedAt;

    @TableField(value = "finish_at")
    private Instant finishAt;

    public Item(Long userId, ItemEditFormVO formVO) {
        BeanUtils.copyProperties(formVO, this);
        this.userId = userId;
    }

    public static ItemVO toItemVO(Item item, Integer modeCode, List<String> images, List<String> tags) {
        ItemVO vo = new ItemVO();

        switch (modeCode) {
            case 2:
                vo.setStateCode(item.getStateCode());
                vo.setFinishAt(item.getFinishAt());
            case 1:
                vo.setDescription(item.getDescription());
                vo.setPostage(item.getPostage());
                vo.setReturnPeriod(item.getReturnPeriod());
                vo.setDeliveryPeriod(item.getDeliveryPeriod());
                vo.setTags(tags);
            case 0:
                vo.setId(item.getId());
                vo.setUserId(item.getUserId());
                vo.setName(item.getName());
                vo.setPrice(item.getPrice());
                vo.setFavorites(item.getFavorites());
                vo.setCreatedAt(item.getCreatedAt());
                vo.setUpdatedAt(item.getUpdatedAt());
                vo.setImages(images);
                break;
            default:
                throw new RuntimeException("modeCode错误");
        }

        return vo;
    }
}
