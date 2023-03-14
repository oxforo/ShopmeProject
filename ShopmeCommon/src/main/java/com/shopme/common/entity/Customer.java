package com.shopme.common.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter
@ToString
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 45)
    private String email;

    @Column(nullable = false, length = 64)
    private String password;

    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 45)
    private String lastName;

    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;

    @Column(nullable = false, length = 64)
    private String addressLine1;

    @Column(name = "address_line_2", length = 64)
    private String addressLine2;

    @Column(nullable = false, length = 45)
    private String city;

    @Column(nullable = false, length = 45)
    private String state;

    @Column(name = "postal_code", nullable = false, length = 10)
    private String postalCode;

    @Column(name = "verification_code", length = 64)
    private String verificationCode;

    private boolean enabled;

    @Column(name = "crated_time")
    private Date createdTime;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @Enumerated(EnumType.STRING)
    @Column(name = "authentication_type", length = 10)
    private AuthenticationType authenticationType;

    @Column(name = "reset_password_token", length = 30)
    private String resetPasswordToken;

    public Customer() {
    }

    public Customer(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Transient
    public String getAddress() {
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
