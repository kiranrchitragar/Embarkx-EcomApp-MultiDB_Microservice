package com.ecommerce.notification.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {

    // this can be read from yml file
    private static final String queueName = "order.queue";
    private static final String exchangeName = "order.exchange";
    private static final String routingKey = "order.tracking";

    @Bean
    public Queue queue(){
        System.out.println("queueName:: " + queueName);
        System.out.println("exchangeName:: " + exchangeName);
        System.out.println("routingKey:: " + routingKey);
        return QueueBuilder.durable(queueName)
                .build();

    }

    @Bean
    public TopicExchange exchange(){
        return ExchangeBuilder.topicExchange(exchangeName)
                .durable(true)
                .build();
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue())
                .to(exchange())
                .with(routingKey);
    }

    // Admin related config
    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory){
        RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        admin.setAutoStartup(true);
        return admin;
    }

    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

//    @Bean
//    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setMessageConverter(messageConverter());
//        rabbitTemplate.setExchange(exchangeName);
//        return rabbitTemplate;
//    }
}
