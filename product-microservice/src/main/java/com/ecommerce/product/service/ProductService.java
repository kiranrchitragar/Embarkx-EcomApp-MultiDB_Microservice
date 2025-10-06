package com.ecommerce.product.service;

import com.ecommerce.product.entities.Product;
import com.ecommerce.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> findProductById(Long id) {
        return productRepository.findByIdAndActiveTrue(id);
    }

    public Optional<Product> updateProduct(Long id, Product reqProduct) {
        return productRepository.findById(id).map(exis -> {
            reqProduct.setId(exis.getId());
            reqProduct.setCreatedAt(exis.getCreatedAt());
            reqProduct.setCreatedBy(exis.getCreatedBy());
            Product productResponse = productRepository.save(reqProduct);
            return productResponse;
        });
    }

    public List<Product> getActiveProducts() {
        return productRepository.findByActiveTrue();
    }

    public List<Product> searchProduct(String keyword) {
        return productRepository.searchProducts(keyword);
    }

    public boolean deleteProduct(Long id) {
        return productRepository.findById(id).map(product -> {
            product.setActive(false);
            productRepository.save(product);
            return true;
        }).orElse(false);
    }
}
