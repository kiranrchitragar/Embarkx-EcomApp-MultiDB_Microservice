package com.ecommerce.order_microservice.helpers;

import com.ecommerce.order_microservice.entities.BaseEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;


public class AuditListner {

    @PrePersist
    public void createAuditUsers(Object entity) {
        if (entity instanceof BaseEntity) {
            ((BaseEntity) entity).setCreatedBy(getCurrentUser());
            ((BaseEntity) entity).setUpdatedBy(getCurrentUser());
        }
    }

    @PreUpdate
    public void updateUpdatedBy(Object entity) {
        if (entity instanceof BaseEntity base) {
            base.setUpdatedBy(getCurrentUser());
        }
    }

    private String getCurrentUser() {
        // Replace with actual logic, e.g. from Spring Security context
        return "ADMIN";
    }
}
