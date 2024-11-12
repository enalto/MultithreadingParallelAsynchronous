package com.enalto.completablefuture;

import com.enalto.domain.Product;
import com.enalto.service.InvetoryService;
import com.enalto.service.ProductInfoService;
import com.enalto.service.ReviewService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceCompletableFutureTest {

    //private final ProductInfoService productInfoService = new ProductInfoService();
    //private final ReviewService reviewService = new ReviewService();

    //ProductServiceCompletableFuture productServiceCompletableFuture =
    //        new ProductServiceCompletableFuture(productInfoService, reviewService);


    @Mock
    private ProductInfoService productInfoService;
    @Mock
    private ReviewService reviewService;
    @Mock
    private InvetoryService invetoryService;

    @InjectMocks
    ProductServiceCompletableFuture productServiceCompletableFuture;

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

    @Test
    void retrieveProductDetailsWithInventory() {
        //scenario
        String productId = "Product 1";

        //action
        Mockito.when(productInfoService.retrieveProductInfo(Mockito.any())).thenCallRealMethod();
        Mockito.when(reviewService.retrieveReviews(Mockito.any())).thenThrow(new RuntimeException("Exception ocurred!"));
        Mockito.when(invetoryService.retrieveInventory(Mockito.any())).thenCallRealMethod();

        Product product = productServiceCompletableFuture.retrieveProductDetailsWithInventory(productId);

        //verification
        Assertions.assertNotNull(product);
        Assertions.assertFalse(product.getProductInfo().getProductOptions().isEmpty());

        product.getProductInfo().getProductOptions().forEach(option -> {
            Assertions.assertNotNull(option.getInventory());
        });

        Assertions.assertNotNull(product.getReview());
        Assertions.assertEquals(0, product.getReview().getNoOfReviews());


    }
}