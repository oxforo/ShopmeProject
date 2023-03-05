package com.shopme.admin.product;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductRestController {

    private final ProductService productService;

    @PostMapping("/products/check_unique")
    public String checkUnique(Integer id, String name) {
        return productService.checkUnique(id, name);
    }
}
