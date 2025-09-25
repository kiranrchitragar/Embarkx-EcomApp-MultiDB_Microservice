package com.ecommerce.user.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
public class Address {
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipcode;
}