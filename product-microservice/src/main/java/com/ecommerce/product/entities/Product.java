package com.ecommerce.product.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="product")
public class Product extends BaseEntity{

    private String name;
    private String description;
    private BigDecimal price;
    @Column(name = "stock_quantity")
    private Integer stockQuantity;
    private String category;
    @Column(name = "image_url")
    private String imageUrl;
    private boolean active;
    }
