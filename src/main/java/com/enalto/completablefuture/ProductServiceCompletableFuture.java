package com.enalto.completablefuture;

import com.enalto.domain.Product;
import com.enalto.domain.ProductInfo;
import com.enalto.domain.Review;
import com.enalto.service.ProductInfoService;
import com.enalto.service.ReviewService;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static com.enalto.util.CommonUtil.stopWatch;

public class ProductServiceCompletableFuture {
    private final ProductInfoService productInfoService;
    private final ReviewService reviewService;

    public ProductServiceCompletableFuture(final ProductInfoService productInfoService, final ReviewService reviewService) {
        Objects.requireNonNull(productInfoService);
        Objects.requireNonNull(reviewService);

        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }

    public Product retrieveProductDetails(String productId) {
        //StopWatch stopWatch = new StopWatch();
        stopWatch.start();
//        ProductInfo productInfo = productInfoService.retrieveProductInfo(productId);
//        Review review = reviewService.retrieveReviews(productId);

        CompletableFuture<ProductInfo> productInfoCompletableFuture =
                CompletableFuture.supplyAsync(() -> productInfoService.retrieveProductInfo(productId));

        CompletableFuture<Review> reviewCompletableFuture =
                CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId));

        Product product = productInfoCompletableFuture.thenCombine(reviewCompletableFuture,
                (productInfo, review) -> new Product(productId, productInfo, review)
        ).join();

        stopWatch.stop();
        System.out.println(stopWatch.getElapsedTime());
        return product;
    }

    public CompletableFuture<Product> retrieveProductDetailsNonBlocking(String productId) {
        Objects.requireNonNull(productId);

        CompletableFuture<ProductInfo> productInfoCompletableFuture =
                CompletableFuture.supplyAsync(() -> productInfoService.retrieveProductInfo(productId));

        CompletableFuture<Review> reviewCompletableFuture =
                CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId));

        return productInfoCompletableFuture.thenCombine(reviewCompletableFuture,
                (productInfo, review) -> new Product(productId, productInfo, review)
        );
    }
}
