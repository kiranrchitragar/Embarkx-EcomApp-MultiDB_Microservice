package com.ecommerce.order.service;

import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.dto.ProductResponse;
import com.ecommerce.order.dto.UserResponse;
import com.ecommerce.order.entities.CartItem;
import com.ecommerce.order.repository.CartItemRepository;
import com.ecommerce.order.restClient.ProductServiceClient;
import com.ecommerce.order.restClient.UserServiceClient;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class CartItemService {

    private final UserServiceClient userServiceClient;
    private final CartItemRepository cartItemRepository;
    private final ProductServiceClient productServiceClient;

    public CartItemService(UserServiceClient userServiceClient, CartItemRepository cartItemRepository, ProductServiceClient productServiceClient) {
        this.userServiceClient = userServiceClient;
        this.cartItemRepository = cartItemRepository;
        this.productServiceClient = productServiceClient;
    }

    public boolean addToCart(String userId, CartItemRequest cartItemRequest) {

        // Validate for product id - Call Product Microservice
        ProductResponse productResponse = productServiceClient.getProductDetailsById(cartItemRequest.getProductId());
        if(productResponse ==null){
            return false;
        }
        if(productResponse.getStockQuantity() < cartItemRequest.getQuantity()){
            return false;
        }

        // Validate user
        UserResponse userResponse = userServiceClient.getUserDetailsById(String.valueOf(userId));
        if(userResponse ==null){
            return false;
        }
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

    public boolean deleteItemFromCart(String userId, Long productId) {

        CartItem cartItem = cartItemRepository.findByUserIdAndProductId(userId,productId);
       if(cartItem!=null){
           cartItemRepository.deleteByUserIdAndProductId(userId,productId);
           return true;
       }
        return false;
    }

    public List<CartItem> getCartItemsForUser(String userId) {
        return cartItemRepository.findByUserId(userId);
    }

    public void clearCart(String userId) {
        cartItemRepository.deleteByUserId(userId);
    }
}
