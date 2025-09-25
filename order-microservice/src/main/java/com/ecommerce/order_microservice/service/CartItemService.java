package com.ecommerce.order_microservice.service;

import com.ecommerce.order_microservice.dto.CartItemRequest;
import com.ecommerce.order_microservice.entities.CartItem;
import com.ecommerce.order_microservice.repository.CartItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartItemService {

    //private final ProductRepository productRepository;
    // private final UsersRepository usersRepository;
    private final CartItemRepository cartItemRepository;

    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public boolean addToCart(Long userId, CartItemRequest cartItemRequest) {

        CartItem existingCartItem = cartItemRepository.findByUserIdAndProductId(userId,cartItemRequest.getProductId());
        if(existingCartItem!=null){
            //update the quantity, product already exists in cart , so we update the quantity
            existingCartItem.setQuantity(existingCartItem.getQuantity()+cartItemRequest.getQuantity());
            //existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            existingCartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepository.save(existingCartItem);
        }else{
            // Create new cart item
            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(cartItemRequest.getProductId());
            cartItem.setQuantity(cartItemRequest.getQuantity());
            //cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(cartItemRequest.getQuantity())));
            cartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepository.save(cartItem);
        }
        return true;
    }

    public boolean deleteItemFromCart(Long userId, Long productId) {

        CartItem cartItem = cartItemRepository.findByUserIdAndProductId(userId,productId);
       if(cartItem!=null){
           cartItemRepository.deleteByUserIdAndProductId(userId,productId);
           return true;
       }
        return false;
    }

    public List<CartItem> getCartItemsForUser(Long userId) {
        return cartItemRepository.findByUserId(userId);
    }

    public void clearCart(Long userId) {
        cartItemRepository.deleteByUserId(userId);
    }
}
