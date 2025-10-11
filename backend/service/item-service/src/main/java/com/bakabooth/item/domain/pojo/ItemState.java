package com.bakabooth.item.domain.pojo;


public enum ItemState {
    ALL,                // 全部
    DRAFT,              // 草稿
    ON_SALE,            // 在售
    RESERVED,           // 已下单未付款（暂时锁定）
    OFF_SHELF,          // 已下架
    SOLD_OUT,           // 已售出
    DELETED;            // 已删除

    public boolean canBuy() {
        return this == ON_SALE;
    }
}