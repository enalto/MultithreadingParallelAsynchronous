package com.enalto.service;

import com.enalto.domain.Review;
import com.enalto.util.CommonUtil;

public class ReviewService {

    public Review retrieveReviews(String productId) {
        CommonUtil.delay(1000);
        return new Review(200, 4.5);
    }

}
