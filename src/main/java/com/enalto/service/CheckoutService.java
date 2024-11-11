package com.enalto.service;

import com.enalto.domain.checkout.Cart;
import com.enalto.domain.checkout.CartItem;
import com.enalto.domain.checkout.CheckoutResponse;
import com.enalto.domain.checkout.CheckoutStatus;
import com.enalto.util.StopWatch;

import java.util.List;
import java.util.stream.Collectors;

public class CheckoutService {

    private final PriceValidatorService priceValidatorService;

    public CheckoutService(PriceValidatorService priceValidatorService) {
        this.priceValidatorService = priceValidatorService;
    }

    public CheckoutResponse checkout(Cart cart) {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<CartItem> priceValidationList = cart.getCartItemList()
                .stream()
                .parallel()
                .map(cartItem -> {
                    boolean isPriceInvalid = priceValidatorService.isCartItemInvalid(cartItem);
                    cartItem.setExpired(isPriceInvalid);
                    return cartItem;
                })
                .filter(CartItem::isExpired)
                .collect(Collectors.toList());

        stopWatch.stop();
        System.out.println(stopWatch.getElapsedTime());

        if (!priceValidationList.isEmpty()) {
            return new CheckoutResponse(CheckoutStatus.FAILURE, priceValidationList);
        }
        return new CheckoutResponse(CheckoutStatus.SUCCESS);
    }
}
