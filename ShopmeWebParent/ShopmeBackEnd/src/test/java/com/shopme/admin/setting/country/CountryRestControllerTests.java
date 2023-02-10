package com.shopme.admin.setting.country;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopme.common.entity.Country;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.persistence.EntityManager;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CountryRestControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    EntityManager entityManager;

    @Autowired
    CountryRepository countryRepository;

    @Test
    @WithMockUser(username = "nam@codejava.net", password = "something", roles = "ADMIN")
    public void testListCountries() throws Exception {
        String url = "/countries/list";
        MvcResult result = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();

        Country[] countries = objectMapper.readValue(jsonResponse, Country[].class);

        assertThat(countries).hasSizeGreaterThan(0);
    }

    @Test
    @WithMockUser(username = "nam@codejava.net", password = "something", roles = "ADMIN")
    public void testCreateCountry() throws Exception {
        String url = "/countries/save";
        String countryName = "Germany";
        String countryCode = "DE";
        Country country = new Country(countryName, countryCode);

        MvcResult result = mockMvc.perform(post(url).contentType("application/json")
                        .content(objectMapper.writeValueAsString(country))
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        Integer countryId = Integer.parseInt(response);
        Country savedCountry = entityManager.find(Country.class, countryId);

        assertThat(savedCountry.getName()).isEqualTo(countryName);
    }

    @Test
    @WithMockUser(username = "nam@codejava.net", password = "something", roles = "ADMIN")
    public void testUpdateCountry() throws Exception {
        String url = "/countries/save";

        Integer countryId = 5;
        String countryName = "China";
        String countryCode = "CN";
        Country country = new Country(countryId, countryName, countryCode);

        MvcResult result = mockMvc.perform(post(url).contentType("application/json")
                        .content(objectMapper.writeValueAsString(country))
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(countryId)))
                .andReturn();

        Country savedCountry = entityManager.find(Country.class, countryId);

        assertThat(savedCountry.getName()).isEqualTo(countryName);
    }

    @Test
    @WithMockUser(username = "nam@codejava.net", password = "something", roles = "ADMIN")
    public void testDeleteCountry() throws Exception {
        Integer countryId = 5;
        String url = "/countries/delete/" + countryId;
        mockMvc.perform(get(url)).andExpect(status().isOk());

        Optional<Country> findById = countryRepository.findById(countryId);

        assertThat(findById).isNotPresent();
    }
}