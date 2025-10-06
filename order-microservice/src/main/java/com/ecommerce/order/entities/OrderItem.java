package com.ecommerce.order.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.math.BigDecimal;


@Entity(name="order_item")
@Data
@ToString(exclude = "order")
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem extends BaseEntity{

    private Long productId;
    private Integer quantity;
    private BigDecimal price;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id",nullable = false)
    private Order order;
}
