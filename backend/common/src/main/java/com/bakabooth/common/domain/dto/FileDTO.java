package com.bakabooth.common.domain.dto;

import lombok.Data;

@Data
public class FileDTO {
    private Long id;
    private String name;
    private Long size;
    private String url;
}
