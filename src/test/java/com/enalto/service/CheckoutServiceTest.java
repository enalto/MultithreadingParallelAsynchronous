package com.enalto.service;

import com.enalto.domain.checkout.Cart;
import com.enalto.domain.checkout.CheckoutResponse;
import com.enalto.domain.checkout.CheckoutStatus;
import com.enalto.domain.checkout.DataSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutServiceTest {

    private final PriceValidatorService priceValidatorService = new PriceValidatorService();
    private final CheckoutService checkoutService = new CheckoutService(priceValidatorService);

    @Test
    void checkout_6_items() {
        //scenario
        Cart cart = DataSet.CreateCart(6);

        //execution
        CheckoutResponse checkoutResponse = checkoutService.checkout(cart);

        //verification
        Assertions.assertEquals(CheckoutStatus.SUCCESS, checkoutResponse.getCheckoutStatus());
    }

    @Test
    void checkout_13_items() {
        //scenario
        Cart cart = DataSet.CreateCart(13);

        //execution
        CheckoutResponse checkoutResponse = checkoutService.checkout(cart);

        //verification
        Assertions.assertEquals(CheckoutStatus.FAILURE, checkoutResponse.getCheckoutStatus());
    }
    @Test
    void checkout_25_items() {
        //scenario
        Cart cart = DataSet.CreateCart(25);

        //execution
        CheckoutResponse checkoutResponse = checkoutService.checkout(cart);

        //verification
        Assertions.assertEquals(CheckoutStatus.FAILURE, checkoutResponse.getCheckoutStatus());
    }
}