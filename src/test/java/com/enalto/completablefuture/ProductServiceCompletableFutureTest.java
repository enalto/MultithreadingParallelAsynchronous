package com.enalto.completablefuture;

import com.enalto.domain.Product;
import com.enalto.service.ProductInfoService;
import com.enalto.service.ReviewService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceCompletableFutureTest {

    private final ProductInfoService productInfoService = new ProductInfoService();
    private final ReviewService reviewService = new ReviewService();

    ProductServiceCompletableFuture productServiceCompletableFuture =
            new ProductServiceCompletableFuture(productInfoService, reviewService);

    @Test
    void retrieveProductDetails() {
        //scenario
        String productId = "ABC1235";

        //action
        Product product = productServiceCompletableFuture.retrieveProductDetails(productId);
        System.out.println(product);

        //verification
        Assertions.assertNotNull(product);
    }

    @Test
    void retrieveProductDetailsNonBlocking() {
        //scenario
        String productId = "Product 1";

        //action
        CompletableFuture<Product> productFuture =
                productServiceCompletableFuture.retrieveProductDetailsNonBlocking(productId);

        //verification
        productFuture.thenAccept(product -> {
            Assertions.assertNotNull(product);

        }).join();

    }


    @Test
    void retrieveProductDetailsProductOptionMustBeGreaterThanZero() {
        //scenario
        String productId = "Product 1";

        //action
        CompletableFuture<Product> productFuture =
                productServiceCompletableFuture.retrieveProductDetailsNonBlocking(productId);

        //verification
        productFuture.thenAccept(product -> {
            Assertions.assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        }).join();

    }

    @Test
    void retrieveProductDetailsNotNullReview() {
        //scenario
        String productId = "Product 1";

        //action
        CompletableFuture<Product> productFuture =
                productServiceCompletableFuture.retrieveProductDetailsNonBlocking(productId);

        //verification
        productFuture.thenAccept(product -> {
            Assertions.assertNotNull(product.getReview());
        }).join();

    }
}