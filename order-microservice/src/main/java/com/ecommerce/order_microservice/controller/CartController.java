package com.ecommerce.order_microservice.controller;


import com.ecommerce.order_microservice.dto.CartItemRequest;
import com.ecommerce.order_microservice.entities.CartItem;
import com.ecommerce.order_microservice.service.CartItemService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@RefreshScope
public class CartController {

    private final CartItemService cartItemService;
    private final String refreshScopeTestValue;

    public CartController(CartItemService cartItemService,
                          @Value("${embarkx.check.refreshScope.value}") String refreshScopeTestValue) {
        this.cartItemService = cartItemService;
        this.refreshScopeTestValue = refreshScopeTestValue;
    }

    @PostMapping("/addToCart")
    public ResponseEntity<String> addToCart(
            @RequestHeader("X-User-ID") Long userId,
            @RequestBody CartItemRequest cartItemRequest) {
        if (!cartItemService.addToCart(userId, cartItemRequest)) {
            return ResponseEntity.badRequest().body("Product Out of stock or User not found or Product not found");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Cart item created successfully");
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<String> removeFromCart(
            @RequestHeader("X-User-ID") Long userId,
            @PathVariable Long productId) {

        boolean removedFromCart = cartItemService.deleteItemFromCart(userId, productId);
        return removedFromCart ? ResponseEntity.status(HttpStatus.OK)
                .body("Item Removed from Cart.")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item Not Found");
    }

    @GetMapping("/items")
    public ResponseEntity<List<CartItem>> getCartItemsForUser(
            @RequestHeader("X-User-ID") Long userId) {
        System.out.println("To Enable to refresh Scope : run -> " +
                "POST -> https://localhost:<port_no>/actuator/refresh");
        System.out.println("refreshScopeTestValue:: " + refreshScopeTestValue);
        return ResponseEntity.ok(cartItemService.getCartItemsForUser(userId));
    }
}
