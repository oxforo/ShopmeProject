package com.shopme.common.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "shipping_rate")
@Getter
@Setter
@ToString
public class ShippingRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private float rate;

    @Column(nullable = false)
    private Integer days;

    @Column(nullable = false)
    private Boolean codSupported = false;


    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @Column(nullable = false)
    private String state;

    public ShippingRate(Integer id, Country country) {
        this.id = id;
        this.country = country;
    }

    public ShippingRate() {

    }
}
