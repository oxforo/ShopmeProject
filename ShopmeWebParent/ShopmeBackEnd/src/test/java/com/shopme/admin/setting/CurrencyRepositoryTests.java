package com.shopme.admin.setting;

import com.shopme.admin.setting.CurrencyRepository;
import com.shopme.common.entity.Currency;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class CurrencyRepositoryTests {

    @Autowired
    CurrencyRepository currencyRepository;

    @Test
    public void testCreateCurrencies() {
        List<Currency> listCurrencies = Arrays.asList(
                new Currency("United States Dollar", "$", "USD"),
                new Currency("British Pound", "£", "GPB"),
                new Currency("Japanese Yen", "¥", "JPY"),
                new Currency("Euro", "€", "EUR"),
                new Currency("Russian Ruble", "₽", "RUB"),
                new Currency("South Korean Won", "₩", "KRW"),
                new Currency("Chinese Yuan", "¥", "CNY"),
                new Currency("Brazilian Dollar", "R$", "BRL"),
                new Currency("Australian Dollar", "$", "AUD"),
                new Currency("Canadian Dollar", "$", "CAD"),
                new Currency("Vietnamese dong", "₫", "VND"),
                new Currency("Indian Rupee", "₹", "INR")
        );

        currencyRepository.saveAll(listCurrencies);
    }

    @Test
    public void testListAllOrderByNameAsc() {
        List<Currency> currencies = currencyRepository.findAllByOrderByNameAsc();

        currencies.forEach(System.out::println);

        assertThat(currencies.size()).isGreaterThan(0);
    }
}