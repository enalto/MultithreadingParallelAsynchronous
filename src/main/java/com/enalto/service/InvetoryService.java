package com.enalto.service;

import com.enalto.domain.Inventory;
import com.enalto.domain.ProductOption;
import com.enalto.util.CommonUtil;

public class InvetoryService {

    public Inventory retrieveInventory(ProductOption productOption) {
        CommonUtil.delay(1000);
        return Inventory.builder()
                .count(2)
                .build();

    }
}
