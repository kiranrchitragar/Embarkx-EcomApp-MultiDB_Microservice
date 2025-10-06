package com.ecommerce.order.entities;

import jakarta.persistence.Entity;
import lombok.Data;

import java.math.BigDecimal;

@Entity(name = "cart_items")
@Data
public class CartItem extends BaseEntity{

    private String userId;
    private Long productId;
    private Integer quantity;
    private BigDecimal price;
}
