package com.enalto.service;

import com.enalto.domain.checkout.CartItem;
import com.enalto.util.CommonUtil;

public class PriceValidatorService {

    public boolean isCartItemInvalid(CartItem cartItem) {
        int cartId = cartItem.getItemId();
        CommonUtil.delay(1000);

        if (cartId == 7 || cartId == 9 || cartId == 11) {
            return true;

        }
        return false;
    }
}
