package com.ecommerce.order_microservice.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Entity(name = "orders")
@Data
@ToString(exclude = "items")
@NoArgsConstructor
public class Order extends BaseEntity{

    private Long userId;
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,orphanRemoval = true)
    // 1 order -> many order items || cascading all the operations to OrderItems
    private List<OrderItem> items = new ArrayList<>();
}
