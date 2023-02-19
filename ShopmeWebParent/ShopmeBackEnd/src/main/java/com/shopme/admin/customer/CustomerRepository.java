package com.shopme.admin.customer;

import com.shopme.common.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Integer> {

    @Query("SELECT c FROM Customer c WHERE CONCAT(c.firstName, ' ', c.lastName, ' ', c.email, ' ',"
            + " c.addressLine1, ' ', c.addressLine2, ' ', c.city, ' ', c.state, ' ', c.country, ' ', c.postalCode)"
            + " LIKE %?1%")
    public Page<Customer> findAll(String keyword, Pageable pageable);

    @Query("UPDATE Customer c SET c.enabled = ?2 WHERE c.id = ?1")
    @Modifying
    void updateEnabledStatus(Integer id, boolean enabled);

    @Query("SELECT c FROM Customer c WHERE c.email = ?1")
    public Customer findByEmail(String email);

    Long countById(Integer id);
}
