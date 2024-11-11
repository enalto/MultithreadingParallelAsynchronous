package com.enalto.domain.checkout;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class DataSet {

    public static Cart CreateCart(int noOfItemsInCart) {
        Cart cart = new Cart();
        List<CartItem> cartItemList = new ArrayList<CartItem>();

        IntStream.range(0, noOfItemsInCart).forEach(index -> {
            String cartItemName = "Cart item ".concat(String.valueOf(index));
            CartItem cartItem = new CartItem(index, cartItemName, index, generateRandomPrice(), false);
            cartItemList.add(cartItem);
        });

        cart.setCartItemList(cartItemList);
        return cart;
    }

    public static double generateRandomPrice() {
        int min = 50;
        int max = 100;
        return (Math.random() * (max - min + 1)) + min;
    }
}
