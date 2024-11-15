package com.enalto.service;

import com.enalto.domain.ProductInfo;
import com.enalto.domain.ProductOption;
import com.enalto.util.CommonUtil;

import java.util.List;

public class ProductInfoService {


    public ProductInfo retrieveProductInfo(String productId) {
        CommonUtil.delay(1000);
        List<ProductOption> productOptions = List.of(new ProductOption(1, "64GB", "Blue", 12.90, null),
                new ProductOption(2, "128GB", "Black", 749.99, null));

        return ProductInfo.builder()
                .productId(productId)
                .productOptions(productOptions)
                .build();

    }
}
