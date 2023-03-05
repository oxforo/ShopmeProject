package com.shopme.admin.category;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryRestController {

    private final CategoryService categoryService;

    @PostMapping("/categories/check_unique")
    public String checkUnique(Integer id, String name, String alias) {
        return categoryService.checkUnique(id, name, alias);
    }

}
