package com.ecommerce.order.entities;

import com.ecommerce.order.helpers.AuditListner;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@EntityListeners(AuditListner.class)
@MappedSuperclass
@Data
public class BaseEntity {

    // This is used to generate id in hexadecimal Value
//    @Id
//    @GeneratedValue(generator = "uuid2")
//    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
//    @Column(name = "id", nullable = false, updatable = false, length = 36)
//    private String id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-increment in DB
    private Long id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime  modifiedAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

}
