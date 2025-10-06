package com.ecommerce.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private AddressResponse addressResponse;
    private UserRoleResponse userrole = UserRoleResponse.CUSTOMER;
    private LocalDateTime createdAt;
    private LocalDateTime  modifiedAt;
    private String createdBy;
    private String updatedBy;
}
