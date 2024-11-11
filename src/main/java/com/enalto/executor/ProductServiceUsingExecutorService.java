package com.enalto.executor;

import com.enalto.domain.Product;
import com.enalto.domain.ProductInfo;
import com.enalto.domain.Review;
import com.enalto.service.ProductInfoService;
import com.enalto.service.ReviewService;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.enalto.util.CommonUtil.stopWatch;

public class ProductServiceUsingExecutorService {
    private final ExecutorService executorService =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private final ProductInfoService productInfoService;
    private final ReviewService reviewService;

    public ProductServiceUsingExecutorService(final ProductInfoService productInfoService, final ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }

    public Product retrieveProductDetails(String productId) throws InterruptedException, ExecutionException {
        stopWatch.start();

        Future<ProductInfo> productInfoFuture = executorService
                .submit(() -> productInfoService.retrieveProductInfo(productId));

        Future<Review> reviewFuture = executorService
                .submit(() -> reviewService.retrieveReviews(productId));


        ProductInfo productInfo = productInfoFuture.get();
        Review review = reviewFuture.get();

        stopWatch.stop();
        System.out.println(stopWatch.getElapsedTime());
        if(!executorService.isShutdown())
            executorService.shutdown();

        System.out.println("Using executorService");
        return new Product(productId, productInfo, review);
    }

    private class ProductInfoRunnable implements Runnable {
        private final String productId;
        private ProductInfo productInfo;

        public ProductInfo getProductInfo() {
            return productInfo;
        }

        public ProductInfoRunnable(String productId) {
            this.productId = productId;
        }

        public void run() {
            this.productInfo = productInfoService.retrieveProductInfo(productId);
        }
    }

    private class ReviewRunnable implements Runnable {
        private final String productId;
        private Review review;


        public Review getReview() {
            return review;
        }

        public ReviewRunnable(String productId) {
            this.productId = productId;
        }

        @Override
        public void run() {
            this.review = reviewService.retrieveReviews(productId);
        }
    }
}
