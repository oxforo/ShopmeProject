package com.shopme.admin.setting.state;

import com.shopme.common.entity.Country;
import com.shopme.common.entity.State;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class StateRepositoryTests {

    @Autowired private StateRepository stateRepository;
    @Autowired private TestEntityManager entityManager;

    @Test
    public void testCreateStatesInIndia() {
        Integer countryId = 1;
        Country country = entityManager.find(Country.class, countryId);

//        State state = stateRepository.save(new State("Karnataka", country));
//        State state = stateRepository.save(new State("Punjab", country));
//        State state = stateRepository.save(new State("Uttar Pradesh", country));
        State state = stateRepository.save(new State("West Bengal", country));

        assertThat(state).isNotNull();
        assertThat(state.getId()).isGreaterThan(0);
    }
}