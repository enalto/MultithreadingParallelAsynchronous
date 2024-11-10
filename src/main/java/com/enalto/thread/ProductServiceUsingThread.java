package com.enalto.thread;

import com.enalto.domain.Product;
import com.enalto.domain.ProductInfo;
import com.enalto.domain.Review;
import com.enalto.service.ProductInfoService;
import com.enalto.service.ReviewService;
import lombok.Getter;

import static com.enalto.util.CommonUtil.stopWatch;

public class ProductServiceUsingThread {
    private final ProductInfoService productInfoService;
    private final ReviewService reviewService;

    public ProductServiceUsingThread(final ProductInfoService productInfoService, final ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }

    public Product retrieveProductDetails(String productId) throws InterruptedException {
        stopWatch.start();

        ProductInfoRunnable productInfoRunnable = new ProductInfoRunnable(productId);
        Thread productInfoThread = new Thread(productInfoRunnable);

        ReviewRunnable reviewRunnable = new ReviewRunnable(productId);
        Thread reviewThread = new Thread(reviewRunnable);

        productInfoThread.start();
        reviewThread.start();

        productInfoThread.join();
        reviewThread.join();

        ProductInfo productInfo = productInfoRunnable.getProductInfo();
        Review review = reviewRunnable.getReview();

        stopWatch.stop();
        System.out.println(stopWatch.getElapsedTime());
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
