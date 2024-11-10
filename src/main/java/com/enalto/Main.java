package com.enalto;

import com.enalto.domain.Product;
import com.enalto.service.ProductInfoService;
import com.enalto.service.ReviewService;
import com.enalto.thread.ProductServiceUsingThread;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        //ProductService productService = new ProductService(productInfoService, reviewService);
        ProductServiceUsingThread productService =
                new ProductServiceUsingThread(productInfoService, reviewService);

        String productId = "ABCD";
        Product product = productService.retrieveProductDetails(productId);
        System.out.println(product);
    }
}