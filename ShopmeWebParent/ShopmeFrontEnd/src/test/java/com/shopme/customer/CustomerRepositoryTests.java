package com.shopme.customer;

import com.shopme.common.entity.AuthenticationType;
import com.shopme.common.entity.Country;
import com.shopme.common.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class CustomerRepositoryTests {

    @Autowired private CustomerRepository customerRepository;
    @Autowired private TestEntityManager entityManager;

    @Test
    public void testCreateCustomer1() {
        Integer countryId = 234; //USA
        Country country = entityManager.find(Country.class, countryId);

        Customer customer = new Customer();
        customer.setCountry(country);
        customer.setFirstName("3David");
        customer.setLastName("3Fountaine");
        customer.setPassword("3password123");
        customer.setEmail("3david.s.fountaine@gmail.com");
        customer.setPhoneNumber("3312-462-7518");
        customer.setAddressLine1("31927 West Drive");
        customer.setCity("3Sacramento");
        customer.setState("3California");
        customer.setVerificationCode("code_123");
        customer.setPostalCode("3code_123");
        customer.setCreatedTime(new Date());

        Customer savedCustomer = customerRepository.save(customer);

        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getId()).isGreaterThan(0);
    }

    @Test
    public void testListCustomers() {
        Iterable<Customer> customers = customerRepository.findAll();
        customers.forEach(System.out::println);

        assertThat(customers).hasSizeGreaterThan(0);
    }

    @Test
    public void testUpdateCustomer() {
        Integer customerId = 1;
        String lastName = "Stanfield";

        Customer customer = customerRepository.findById(customerId).get();
        customer.setLastName(lastName);
        customer.setEnabled(true);

        Customer updatedCustomer = customerRepository.save(customer);
        assertThat(updatedCustomer.getLastName()).isEqualTo(lastName);
    }

    @Test
    public void testGetCustomer() {
        Integer customerId = 2;
        Optional<Customer> findById = customerRepository.findById(customerId);

        assertThat(findById).isPresent();

        Customer customer = findById.get();
        System.out.println("customer = " + customer);
    }

    @Test
    public void testDeleteCustomer() {
        Integer customerId = 2;
        customerRepository.deleteById(customerId);

        Optional<Customer> findById = customerRepository.findById(customerId);
        assertThat(findById).isNotPresent();
    }

    @Test
    public void testFindByEmail() {
        String email = "david.s.fountaine@gmail.com";
        Customer customer = customerRepository.findByEmail(email);

        assertThat(customer).isNotNull();
        System.out.println("customer = " + customer);
    }

    @Test
    public void testFindByVerificationCode() {
        String code = "code_123";
        Customer customer = customerRepository.findByVerificationCode(code);

        assertThat(customer).isNotNull();
        System.out.println("customer = " + customer);
    }

    @Test
    public void testEnabledCustomer() {
        Integer customerId = 1;
        customerRepository.enable(customerId);

        Customer customer = customerRepository.findById(customerId).get();
        assertThat(customer.isEnabled()).isTrue();
    }

    @Test
    public void testUpdateAuthenticationType() {
        Integer id = 1;
        customerRepository.updateAuthenticationType(id, AuthenticationType.DATABASE);

        Customer customer = customerRepository.findById(id).get();
        assertThat(customer.getAuthenticationType()).isEqualTo(AuthenticationType.DATABASE);
    }
}