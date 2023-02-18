package com.shopme.common.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StateDTO {

    private Integer id;
    private String name;

    public StateDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
