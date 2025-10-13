package com.ecommerce.order;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OrderMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderMicroserviceApplication.class, args);
	}

	// this is a test message, without this order.exchange was not showing up in
	// http:localhost:15672
//	@Bean
//	CommandLineRunner runner(RabbitTemplate rabbitTemplate) {
//		return args -> {
//			String message = "Hello RabbitMQ!";
//			rabbitTemplate.convertAndSend("order.exchange", "order.tracking", message);
//			System.out.println("Message sent: " + message);
//		};
//	}
}
