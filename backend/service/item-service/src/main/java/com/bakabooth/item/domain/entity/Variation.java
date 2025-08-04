package com.bakabooth.item.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Variation {
    private String name;
    private Double price;
    private Long stock;
    private Long sales;
}