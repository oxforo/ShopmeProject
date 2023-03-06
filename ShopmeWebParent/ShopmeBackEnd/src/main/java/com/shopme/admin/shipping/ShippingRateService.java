package com.shopme.admin.shipping;

import com.shopme.admin.paging.PagingAndSortingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShippingRateService {

    private static final int SHIPPING_PER_PAGE = 10;
    @Autowired private ShippingRateRepository shippingRateRepository;

    public void listByPage(int pageNum, PagingAndSortingHelper helper) {
        helper.listEntities(pageNum, SHIPPING_PER_PAGE, shippingRateRepository);
    }
}
