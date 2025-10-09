package com.ecommerce.order.controller;


import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.dto.ProductResponse;
import com.ecommerce.order.entities.CartItem;
import com.ecommerce.order.restClient.ProductServiceClient;
import com.ecommerce.order.service.CartItemService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
            @RequestHeader("X-User-ID") String userId,
            @RequestBody CartItemRequest cartItemRequest) {
        if (!cartItemService.addToCart(userId, cartItemRequest)) {
            return ResponseEntity.badRequest().body("Not able to complete the request.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Cart item created successfully");
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<String> removeFromCart(
            @RequestHeader("X-User-ID") String userId,
            @PathVariable Long productId) {

        boolean removedFromCart = cartItemService.deleteItemFromCart(userId, productId);
        return removedFromCart ? ResponseEntity.status(HttpStatus.OK)
                .body("Item Removed from Cart.")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item Not Found");
    }

    @GetMapping("/items")
    public ResponseEntity<List<CartItem>> getCartItemsForUser(
            @RequestHeader("X-User-ID") String userId) {
        System.out.println("To Enable to refresh Scope : run -> " +
                "POST -> https://localhost:<port_no>/actuator/refresh");
        System.out.println("refreshScopeTestValue:: " + refreshScopeTestValue);
        return ResponseEntity.ok(cartItemService.getCartItemsForUser(userId));
    }
}
