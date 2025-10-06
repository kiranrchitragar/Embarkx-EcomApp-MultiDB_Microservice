package com.ecommerce.order.repository;

import com.ecommerce.order.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,String> {

    CartItem findByUserIdAndProductId(String userId, Long productId);

    void deleteByUserIdAndProductId(String userId, Long productId);

    List<CartItem> findByUserId(String userId);

    void deleteByUserId(String userId);
}
