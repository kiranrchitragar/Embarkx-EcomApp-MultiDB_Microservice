package com.ecommerce.order.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressResponse {
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipcode;
}