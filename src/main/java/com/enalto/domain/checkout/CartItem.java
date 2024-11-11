package com.enalto.domain.checkout;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItem {
    private int itemId;
    private String itemName;
    private int quantity;
    private double rate;
    private boolean isExpired;
}
