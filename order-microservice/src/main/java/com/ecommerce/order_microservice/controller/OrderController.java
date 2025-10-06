package com.ecommerce.order_microservice.controller;

import com.ecommerce.order_microservice.entities.Order;
import com.ecommerce.order_microservice.service.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order")
@RefreshScope
public class OrderController {

    private final OrderService orderService;
    private final String refreshScopeTestValue;
    public OrderController(OrderService orderService,
                           @Value("${embarkx.check.refreshScope.value}") String refreshScopeTestValue) {
        this.orderService = orderService;
        this.refreshScopeTestValue = refreshScopeTestValue;
    }

    @PostMapping("/createOrder")
    public ResponseEntity<Order> createOrder(
            @RequestHeader("X-User-ID") Long userId){
        System.out.println("To Enable to refresh Scope : run -> " +
                "POST -> https://localhost:<port_no>/actuator/refresh");
        System.out.println("refreshScopeTestValue:: " + refreshScopeTestValue);
        return orderService.createOrder(userId)
                .map(orderResponse->new ResponseEntity<>(orderResponse,HttpStatus.CREATED))
                .orElseGet(()->ResponseEntity.badRequest().build());
    }
}
