package com.shopme.admin.shipping;

import com.shopme.admin.paging.PagingAndSortingHelper;
import com.shopme.admin.paging.PagingAndSortingParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ShippingRateController {

    @Autowired
    private ShippingRateService shippingRateService;

    @GetMapping("/shipping")
    public String listFirstPage() {
        return "redirect:/shipping/page/1?sortField=name&sortDir=asc";
    }

    @GetMapping("/shipping/page/{pageNum}")
    public String listByPage(
            @PagingAndSortingParam(listName = "listShipping", moduleURL = "/shipping")PagingAndSortingHelper helper,
            @PathVariable(name = "pageNum") int pageNum) {

        shippingRateService.listByPage(pageNum, helper);

        return "shipping/shipping";
    }
}
