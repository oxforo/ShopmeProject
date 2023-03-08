package com.shopme.admin.shippingrate;


import com.shopme.admin.paging.SearchRepository;
import com.shopme.common.entity.Country;
import com.shopme.common.entity.ShippingRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ShippingRateRepository extends SearchRepository<ShippingRate, Integer> {

    @Query("SELECT sr FROM ShippingRate sr WHERE CONCAT(sr.country.name, ' ', sr.state) LIKE %?1%")
    public Page<ShippingRate> findAll(String keyword, Pageable pageable);

    @Query("UPDATE ShippingRate sr SET sr.codSupported = ?2 WHERE sr.id = ?1")
    @Modifying
    void updateCODSupport(Integer id, boolean enabled);

    Long countById(Integer id);

    Long countByCountryAndState(Country country, String state);
}
