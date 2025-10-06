package com.ecommerce.order.service;


import com.ecommerce.order.entities.CartItem;
import com.ecommerce.order.entities.Order;
import com.ecommerce.order.entities.OrderItem;
import com.ecommerce.order.entities.OrderStatus;
import com.ecommerce.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartItemService cartItemService;

    public OrderService(OrderRepository orderRepository, CartItemService cartItemService) {
        this.orderRepository = orderRepository;
        this.cartItemService = cartItemService;
    }

    public Optional<Order> createOrder(String userId) {
        // validate for cart items, user should have items in cart
        List<CartItem> cartItems = cartItemService.getCartItemsForUser(userId);
        if (cartItems.isEmpty()) {
            return Optional.empty();
        }

        //------------------validate for user - valid userid ------------------
//        Optional<Users> userDetailsOptional = usersRepository.findById(userId);
//        if (userDetailsOptional.isEmpty()) {
//            return Optional.empty();
//        }
//        Users users = userDetailsOptional.get();

        // ------------------ calculate total price ------------------
        // get price for each item and sum it all,
        // Get all items from car and add only there price
        BigDecimal totalPrice = cartItems.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // ------------------ create order ------------------
        // Converted all the cartitems to order items
        Order order = new Order();
        order.setUserId(userId);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);
        List<OrderItem> orderItems = cartItems.stream()
                .map(item -> new OrderItem(
                        item.getProductId(),
                        item.getQuantity(),
                        item.getPrice(),
                        order
                ))
                .toList();
        order.setItems(orderItems);
        Order orderResponse = orderRepository.save(order);

        // ------------------ Clear the cart, once order is placed.------------------
        System.out.println("orderResponse:: " + orderResponse);
        cartItemService.clearCart(userId);
        return Optional.of(orderResponse);
    }
}
