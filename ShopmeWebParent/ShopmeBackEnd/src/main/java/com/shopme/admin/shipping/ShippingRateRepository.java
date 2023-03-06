package com.shopme.admin.shipping;


import com.shopme.admin.paging.SearchRepository;
import com.shopme.common.entity.Brand;
import com.shopme.common.entity.ShippingRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShippingRateRepository extends SearchRepository<ShippingRate, Integer> {

    @Query("SELECT s FROM ShippingRate s WHERE CONCAT(s.country.name, ' ', s.state) LIKE %?1%")
    public Page<ShippingRate> findAll(String keyword, Pageable pageable);

    @Query("SELECT NEW ShippingRate(s.id, s.country) FROM ShippingRate s ORDER BY s.country.name ASC")
    public List<ShippingRate> findAll();

}
