package com.ecommerce.order.service;


import com.ecommerce.order.dto.OrderCreatedEvent;
import com.ecommerce.order.entities.CartItem;
import com.ecommerce.order.entities.Order;
import com.ecommerce.order.entities.OrderItem;
import com.ecommerce.order.entities.OrderStatus;
import com.ecommerce.order.repository.OrderRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartItemService cartItemService;
    private final RabbitTemplate rabbitTemplate;

    public OrderService(OrderRepository orderRepository, CartItemService cartItemService, RabbitTemplate rabbitTemplate) {
        this.orderRepository = orderRepository;
        this.cartItemService = cartItemService;
        this.rabbitTemplate = rabbitTemplate;
    }

    public Optional<Order> createOrder(String userId) {
        // validate for cart items, user should have items in cart
        List<CartItem> cartItems = cartItemService.getCartItemsForUser(userId);
        if (cartItems.isEmpty()) {
            return Optional.empty();
        }

        BigDecimal totalPrice = cartItems.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

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

        // sending it to notification service via rabbitMQ
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();
        orderCreatedEvent.setOrderId(orderResponse.getId());
        orderCreatedEvent.setUserId(orderResponse.getUserId());
        orderCreatedEvent.setStatus(orderResponse.getStatus());
        orderCreatedEvent.setTotalAmount(orderResponse.getTotalAmount());
        orderCreatedEvent.setItems(orderResponse.getItems());
        orderCreatedEvent.setCreatedAt(orderResponse.getCreatedAt());

        rabbitTemplate.convertAndSend("order.exchange",
                "order.tracking",orderCreatedEvent);
        return Optional.of(orderResponse);
    }
}
