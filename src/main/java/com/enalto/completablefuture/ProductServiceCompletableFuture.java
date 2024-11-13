package com.enalto.completablefuture;

import com.enalto.domain.*;
import com.enalto.service.InvetoryService;
import com.enalto.service.ProductInfoService;
import com.enalto.service.ReviewService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.enalto.util.CommonUtil.stopWatch;

@Slf4j
public class ProductServiceCompletableFuture {
    private final ProductInfoService productInfoService;
    private final ReviewService reviewService;
    private InvetoryService invetoryService = new InvetoryService();

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
                CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId))
                        .exceptionally(exception -> {
                            log.info(exception.getMessage());
                            return Review.builder()
                                    .noOfReviews(0)
                                    .overallRatings(0.00)
                                    .build();
                        });

        Product product = productInfoCompletableFuture.thenCombine(reviewCompletableFuture,
                        (productInfo, review) -> new Product(productId, productInfo, review)

                )
                .whenComplete((product1, throwable) -> {
                    log.info("ProductInfo {}, and exceptions is {}", product1, throwable.getMessage());
                })
                .join();

        stopWatch.stop();
        System.out.println(stopWatch.getElapsedTime());
        return product;
    }

    private List<ProductOption> updateInventory(ProductInfo productInfo) {
        List<CompletableFuture<ProductOption>> productOptionList = productInfo.getProductOptions()
                .stream()
                .map(productOption ->
                        CompletableFuture.supplyAsync(() -> invetoryService.retrieveInventory(productOption))
                                .exceptionally(exception -> {
                                    log.info("Exception in Inventory Service : " + exception.getMessage());
                                    return Inventory.builder().count(1).build();
                                })
                                .thenApply((inventory -> {
                                    productOption.setInventory(inventory);
                                    return productOption;
                                }))).collect(Collectors.toList());

        CompletableFuture<Void> cfAllOf = CompletableFuture.allOf(productOptionList.toArray(new CompletableFuture[productOptionList.size()]));
        return cfAllOf
                .thenApply(v -> {
                    return productOptionList.stream().map(CompletableFuture::join)
                            .collect(Collectors.toList());
                }).join();

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
