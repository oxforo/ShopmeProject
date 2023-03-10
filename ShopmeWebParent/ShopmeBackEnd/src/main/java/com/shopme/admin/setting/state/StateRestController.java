package com.shopme.admin.setting.state;

import com.shopme.common.entity.Country;
import com.shopme.common.entity.State;
import com.shopme.common.entity.StateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StateRestController {

    @Autowired
    StateRepository stateRepository;

    @GetMapping("/states/list_by_country/{id}")
    public List<StateDTO> listAll(@PathVariable("id") Integer countryId) {
        List<State> listStates = stateRepository.findByCountryOrderByNameAsc(new Country(countryId));
        List<StateDTO> result = new ArrayList<>();

        for (State state : listStates) {
            result.add(new StateDTO(state.getId(), state.getName()));
        }

        return result;
    }

    @PostMapping("/states/save")
    public String save(@RequestBody State state) {
        System.out.println("state = " + state.toString());
        State savedState = stateRepository.save(state);

        return String.valueOf(savedState.toString());
    }

    @DeleteMapping("/states/delete/{id}")
    public void delete(@PathVariable("id") Integer id) {
        stateRepository.deleteById(id);
    }
}
