package com.ecommerce.notification;

import com.ecommerce.notification.payload.OrderCreatedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class OrderEventConsumer {
    // this can be read from yml file
    @RabbitListener(queues = "order.queue")
    public void handleOrderEvent(OrderCreatedEvent orderCreatedEvent){
        System.out.println("Received Order Event : " + orderCreatedEvent.toString());
    }
}
