package com.enalto.service;

import com.enalto.domain.Product;
import com.enalto.domain.ProductInfo;
import com.enalto.domain.Review;
import com.enalto.util.StopWatch;

import static com.enalto.util.CommonUtil.stopWatch;

public class ProductService {
    private final ProductInfoService productInfoService;
    private final ReviewService reviewService;

    public ProductService(final ProductInfoService productInfoService, final ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }

    public Product retrieveProductDetails(String productId) {
        //StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ProductInfo productInfo = productInfoService.retrieveProductInfo(productId);
        Review review = reviewService.retrieveReviews(productId);

        stopWatch.stop();
        System.out.println(stopWatch.getElapsedTime());
        return new Product(productId, productInfo, review);
    }
}
