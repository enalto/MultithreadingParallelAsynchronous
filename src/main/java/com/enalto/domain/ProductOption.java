package com.enalto.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductOption {
    private long productOptionId;
    private String size;
    private String color;
    private double price;
    private Inventory inventory;
}
