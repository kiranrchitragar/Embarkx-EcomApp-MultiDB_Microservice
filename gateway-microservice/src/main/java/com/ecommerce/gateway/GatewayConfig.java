package com.ecommerce.gateway;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

// Either Java code routes can be used or we can use the routes from gateway-service.yml
// @Component
public class GatewayConfig {
/*
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route("product-service",r->r
                        .path("/api/v1/product/**")
                        .uri("lb://PRODUCT-MICROSERVICE"))
                .route("user-service",r->r
                        .path("/api/v1/user/**")
                        .uri("lb://USER-MICROSERVICE"))
                .route("order-service",r->r
                        .path("/api/v1/order/**,/api/v1/cart/**")
                        .uri("lb://ORDER-MICROSERVICE"))
                .build();
    }

 */
}
