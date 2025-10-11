package com.tamakara.booth.backend.service.item.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class ItemPageVO {
    private List<ItemVO> records;
    private long total;
    private long size;
    private long current;
}
