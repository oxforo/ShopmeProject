package com.shopme.admin.shipping;


import com.shopme.admin.paging.SearchRepository;
import com.shopme.common.entity.Country;
import com.shopme.common.entity.ShippingRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ShippingRateRepository extends SearchRepository<ShippingRate, Integer> {

    @Query("SELECT s FROM ShippingRate s WHERE CONCAT(s.country.name, ' ', s.state) LIKE %?1%")
    public Page<ShippingRate> findAll(String keyword, Pageable pageable);

    @Query("UPDATE ShippingRate s SET s.codSupported = ?2 WHERE s.id = ?1")
    @Modifying
    void updateEnabledStatus(Integer id, boolean codSupported);

    Long countById(Integer id);

    Long countByCountryAndState(Country country, String state);
}
