package com.bakabooth.file.domain.vo;

import lombok.Data;

@Data
public class FileVO {
    private Long id;
    private String name;
    private Long size;
    private String url;
}
