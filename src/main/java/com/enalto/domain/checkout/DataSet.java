package com.enalto.domain.checkout;

import java.util.ArrayList;
import java.util.LinkedList;
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

    public static ArrayList<Integer> generateArrayList(int maxNumber) {
        ArrayList<Integer> integerArrayList = new ArrayList<>();

        IntStream.rangeClosed(1, maxNumber)
                .boxed()
                .forEach(integerArrayList::add);

        return integerArrayList;
    }

    public static LinkedList<Integer> generateLinkedList(int maxNumber) {
        LinkedList<Integer> linkedList = new LinkedList<>();
        IntStream.rangeClosed(1, maxNumber)
                .boxed()
                .forEach(linkedList::add);
        return linkedList;
    }
}
