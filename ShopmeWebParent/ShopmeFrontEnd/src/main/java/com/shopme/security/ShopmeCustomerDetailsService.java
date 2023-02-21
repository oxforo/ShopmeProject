package com.shopme.security;

import com.shopme.common.entity.Customer;
import com.shopme.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class ShopmeCustomerDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository CustomerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = CustomerRepository.findByEmail(email);
        if (customer != null) {
            return new ShopmeCustomerDetails(customer);
        }

        throw new UsernameNotFoundException("Could not find customer with email: " + email);
    }
}
