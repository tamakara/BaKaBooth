package com.bakabooth.file.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("permission")
public class Permission {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "file_id")
    private Long fileId;

    @TableField(value = "user_id")
    private Long userId;
}
