package com.shopme.common.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "address")
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "first_name", length = 45)
    private String firstName;

    @Column(name = "last_name", length = 45)
    private String lastName;

    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @Column(name = "address_line1", length = 64)
    private String addressLine1;

    @Column(name = "address_line2", length = 64)
    private String addressLine2;

    @Column(length = 45)
    private String city;

    @Column(length = 45)
    private String state;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "default_address")
    private boolean defaultForShipping;

    public boolean isDefaultForShipping() {
        return defaultForShipping;
    }
    @Override
    public String toString() {

        String address = firstName;

        if(lastName != null && !lastName.isEmpty()) address += " " + lastName;

        if(!addressLine1.isEmpty()) address += ", " + addressLine1;

        if(addressLine2 != null && !addressLine2.isEmpty()) address += " " + addressLine2;

        if(!city.isEmpty()) address += ", " + city;

        if(state != null && !state.isEmpty()) address += " " + state;

        address += ", " + country.getName();

        if(!postalCode.isEmpty()) address += ". Postal Code: " + postalCode;
        if(!phoneNumber.isEmpty()) address += ". Phone Number: " + phoneNumber;

        return address;
    }
}
