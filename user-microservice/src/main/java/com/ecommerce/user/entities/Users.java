package com.ecommerce.user.entities;


import lombok.Data;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection ="users")
public class Users{

    @Id
    private String id;
    private String firstname;
    private String lastname;

    @Indexed(unique = true)
    private String email;
    private String phone;
    private Address address;
    private UserRole userrole = UserRole.CUSTOMER;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime  modifiedAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;
}
