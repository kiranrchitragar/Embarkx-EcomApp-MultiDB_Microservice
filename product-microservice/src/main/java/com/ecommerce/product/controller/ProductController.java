package com.ecommerce.product.controller;

import com.ecommerce.product.entities.Product;
import com.ecommerce.product.service.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/product")
@RefreshScope
public class ProductController {

    private final ProductService productService;
    private final String refreshScopeTestValue;

    public ProductController(ProductService productService,
                             @Value("${embarkx.check.refreshScope.value}") String refreshScopeTestValue) {
        this.productService = productService;
        this.refreshScopeTestValue = refreshScopeTestValue;
    }

    @GetMapping("/allProducts")
    public ResponseEntity<List<Product>> getAllUserDetails() {
        System.out.println("To Enable to refresh Scope : run -> " +
                "POST -> https://localhost:<port_no>/actuator/refresh");
        System.out.println("refreshScopeTestValue:: " + refreshScopeTestValue);
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/activeProducts")
    public ResponseEntity<List<Product>> getActiveProducts() {
        System.out.println("To Enable to refresh Scope : run -> " +
                "POST -> https://localhost:<port_no>/actuator/refresh");
        System.out.println("refreshScopeTestValue:: " + refreshScopeTestValue);
        return new ResponseEntity<>(productService.getActiveProducts(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        boolean deleted = productService.deleteProduct(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/searchProduct")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {
        return new ResponseEntity<>(productService.searchProduct(keyword), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable Long id) {
        return productService.findProductById(id)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @PostMapping("/createProduct")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return new ResponseEntity<>(productService.createProduct(product), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/updateProduct")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id, @RequestBody Product product) {
        return productService.updateProduct(id, product).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
