package com.enalto.completablefuture;

import com.enalto.domain.*;
import com.enalto.service.InvetoryService;
import com.enalto.service.ProductInfoService;
import com.enalto.service.ReviewService;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.enalto.util.CommonUtil.stopWatch;

public class ProductServiceCompletableFuture {
    private final ProductInfoService productInfoService;
    private final ReviewService reviewService;
    private InvetoryService invetoryService;

    public ProductServiceCompletableFuture(final ProductInfoService productInfoService, final ReviewService reviewService) {
        Objects.requireNonNull(productInfoService);
        Objects.requireNonNull(reviewService);

        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }

    public ProductServiceCompletableFuture(ProductInfoService productInfoService,
                                           ReviewService reviewService, InvetoryService invetoryService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
        this.invetoryService = invetoryService;
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

    public Product retrieveProductDetailsWithInventory(String productId) {
        stopWatch.start();

        CompletableFuture<ProductInfo> productInfoCompletableFuture =
                CompletableFuture.supplyAsync(() -> productInfoService.retrieveProductInfo(productId))
                        .thenApply(productInfo -> {
                            List<ProductOption> productOptions = updateInventory(productInfo);
                            productInfo.setProductOptions(productOptions);
                            return productInfo;
                        });

        CompletableFuture<Review> reviewCompletableFuture =
                CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId));

        Product product = productInfoCompletableFuture.thenCombine(reviewCompletableFuture,
                (productInfo, review) -> new Product(productId, productInfo, review)
        ).join();

        stopWatch.stop();
        System.out.println(stopWatch.getElapsedTime());
        return product;
    }

    private List<ProductOption> updateInventory(ProductInfo productInfo) {
        return productInfo.getProductOptions()
                .stream()
                .map((productOption) -> {
                    Inventory inventory = invetoryService.retrieveInventory(productOption);
                    productOption.setInventory(inventory);
                    return productOption;
                }).collect(Collectors.toList());
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
