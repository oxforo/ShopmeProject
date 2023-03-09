package com.shopme.address;

import com.shopme.common.entity.Address;
import com.shopme.common.entity.Country;
import com.shopme.common.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class AddressRepositoryTests {

    @Autowired
    private AddressRepository addressRepository;

    @Test
    void addNewTest() {

        //given
        Integer customerId = 5;
        Integer countryId = 234;

        Address address = new Address();
        address.setCountry(new Country(countryId));
        address.setCustomer(new Customer(customerId));
        address.setFirstName("Sumin");
        address.setLastName("Chae");
        address.setPhoneNumber("010-1234-5678");
        address.setAddressLine1("dong-jack-Gu");
        address.setAddressLine2("Sadang-Dong");
        address.setCity("Seoul");
        address.setState("Korea");
        address.setPostalCode("1234");
        address.setDefaultAddress(Boolean.FALSE);


        //when
        Address savedAddress = addressRepository.save(address);

        //then
        assertThat(savedAddress).isNotNull();
        assertThat(savedAddress.getId()).isGreaterThan(0);
    }

    @Test
    void findByCustomerTest() {
        Integer customerId = 5;
        List<Address> listAddresses = addressRepository.findByCustomer(new Customer(customerId));

        assertThat(listAddresses.size()).isGreaterThan(0);

        listAddresses.forEach(System.out::println);
    }
    @Test
    void findByIdAndCustomerTest() {
        Integer addressId = 1;
        Integer customerId = 5;

        Address address = addressRepository.findByIdAndCustomer(addressId, customerId);
        assertThat(address).isNotNull();
        System.out.println("address = " + address);
    }

    @Test
    void updateTest() {
        Integer addressId = 1;
        String phoneNumber = "123-4567-890";

        Address address = addressRepository.findById(addressId).get();
        address.setPhoneNumber(phoneNumber);

        Address updatedAddress = addressRepository.save(address);
        assertThat(updatedAddress.getPhoneNumber()).isEqualTo(phoneNumber);
    }
    @Test
    void deleteByIdAndCustomerTest() {
        Integer addressId = 1;
        Integer customerId = 5;

        addressRepository.deleteByIdAndCustomer(addressId, customerId);
        Address address = addressRepository.findByIdAndCustomer(addressId, customerId);

        assertThat(address).isNull();
    }
}