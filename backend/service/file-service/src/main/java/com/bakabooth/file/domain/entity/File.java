package com.bakabooth.file.domain.entity;

import com.bakabooth.file.util.HashUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;

@Data
@TableName("file")
public class File {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "name")
    private String name;

    @TableField(value = "hash")
    private String hash;

    @TableField(value = "size")
    private Long size;

    @TableField(value = "is_public")
    private Boolean isPublic;

    @TableField(value = "created_at")
    private Instant createdAt;

    public File(Long userId, MultipartFile multipartFile) {
        this.userId = userId;
        this.name = multipartFile.getOriginalFilename();
        this.hash = HashUtil.calculateHash(multipartFile);
        this.size = multipartFile.getSize();
    }
}
