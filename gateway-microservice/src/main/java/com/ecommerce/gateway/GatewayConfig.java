package com.ecommerce.gateway;

import org.apache.http.protocol.HTTP;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

// Either Java code routes can be used or we can use the routes from gateway-service.yml
@Component
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route("product-service",r->r
                        .path("/api/v1/product/**")
                        .filters(f->f

                                // this is for retry
                                .retry(retryConfig -> retryConfig
                                        .setRetries(10)
                                        .setMethods(HttpMethod.GET))
                                // this is for circuit  breaker
                                .circuitBreaker(config->config
                                .setName("gatewayCircuitBreaker")
                                .setFallbackUri("forward:/fallback/products")))

                        .uri("lb://PRODUCT-MICROSERVICE"))
                .route("user-service",r->r
                        .path("/api/v1/user/**")
                        .filters(f->f.circuitBreaker(config->config
                                .setName("gatewayCircuitBreaker")
                                .setFallbackUri("forward:/fallback/users")))
                        .uri("lb://USER-MICROSERVICE"))
                .route("order-service",r->r
                        .path("/api/v1/order/**","/api/v1/cart/**")
                        .filters(f->f.circuitBreaker(config->config
                                .setName("gatewayCircuitBreaker")
                                .setFallbackUri("forward:/fallback/orders")))
                        .uri("lb://ORDER-MICROSERVICE"))
                .build();
    }
}
