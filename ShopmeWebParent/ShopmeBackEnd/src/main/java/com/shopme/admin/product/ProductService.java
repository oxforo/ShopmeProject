package com.shopme.admin.product;

import com.shopme.admin.paging.PagingAndSortingHelper;
import com.shopme.common.entity.Product;
import com.shopme.common.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    public static final int PRODUCTS_PER_PAGE = 5;

    private final ProductRepository productRepository;

    public List<Product> listAll() {
        return (List<Product>) productRepository.findAll();
    }

    public void listByPage(int pageNum, PagingAndSortingHelper helper, Integer categoryId) {

        Pageable pageable = helper.createPageable(PRODUCTS_PER_PAGE, pageNum);
        String keyword = helper.getKeyword();
        Page<Product> page = null;

        if (keyword != null && !keyword.isEmpty()) {
            if (categoryId != null && categoryId > 0) {
                String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
                page = productRepository.searchInCategory(categoryId, categoryIdMatch, keyword, pageable);
            } else {
                page = productRepository.findAll(keyword, pageable);
            }
        } else {
            if (categoryId != null && categoryId > 0) {
                String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
                page = productRepository.findAllInCategory(categoryId, categoryIdMatch, pageable);
            } else {
                page = productRepository.findAll(pageable);
            }
        }

        helper.updateModelAttributes(pageNum, page);
    }

    public Product save(Product product) {
        if (product.getId() == null) {
            product.setCreatedTime(new Date());
        }

        if (product.getAlias() == null || product.getAlias().isEmpty()) {
            String defaultAlias = product.getName().replaceAll(" ", "-");
            product.setAlias(defaultAlias);
        } else {
            product.setAlias(product.getAlias().replaceAll(" ", "-"));
        }

        product.setUpdatedTime(new Date());

        return productRepository.save(product);
    }

    public void saveProductPrice(Product productInForm) {
        Product productInDB = productRepository.findById(productInForm.getId()).get();
        productInDB.setCost(productInForm.getCost());
        productInDB.setPrice(productInForm.getPrice());
        productInDB.setDiscountPercent(productInForm.getDiscountPercent());

        productRepository.save(productInDB);
    }

    public String checkUnique(Integer id, String name) {

        boolean isCreatingNew = (id == null || id == 0);

        Product productByName = productRepository.findByName(name);

        if (isCreatingNew) {
            if (productByName != null) {
                return "DuplicateName";
            }
        } else {
            if (productByName != null && productByName.getId() != id) {
                return "DuplicateName";
            }
        }

        return "OK";
    }

    public void updateUserEnabledStatus(Integer id, boolean enabled) {
        productRepository.updateEnabledStatus(id, enabled);
    }

    public void delete(Integer id) throws ProductNotFoundException {
        Long countById = productRepository.countById(id);
        if (countById == null || countById == 0) {
            throw new ProductNotFoundException("Could not find any category with ID " + id);
        }
        productRepository.deleteById(id);
    }

    public Product get(Integer id) throws ProductNotFoundException {
        try {
            return productRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new ProductNotFoundException("Colud not find any product with ID " + id);
        }
    }
}
