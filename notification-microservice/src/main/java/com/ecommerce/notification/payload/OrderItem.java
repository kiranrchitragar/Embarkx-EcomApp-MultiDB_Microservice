package com.ecommerce.notification.payload;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
public class OrderItem{
    private Long productId;
    private Integer quantity;
    private BigDecimal price;
}
