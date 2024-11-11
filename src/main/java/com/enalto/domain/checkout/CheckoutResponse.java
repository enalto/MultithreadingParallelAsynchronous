package com.enalto.domain.checkout;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CheckoutResponse {

    private CheckoutStatus checkoutStatus;
    private List<CartItem> errorList = new ArrayList<>();

    public CheckoutResponse(CheckoutStatus checkoutStatus) {
        this.checkoutStatus = checkoutStatus;
    }

    public CheckoutResponse(CheckoutStatus cheoutStatus, List<CartItem> errorList) {
        this.checkoutStatus = cheoutStatus;
        this.errorList = errorList;
    }
}
