package com.shopme.common.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(name = "countries")
@Entity
@Getter
@Setter
@ToString
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private String code;

    @OneToMany(mappedBy = "country")
    private Set<State> states = new HashSet<>();

    public Country() {
    }

    public Country(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
