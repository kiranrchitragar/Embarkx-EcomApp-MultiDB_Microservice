package com.ecommerce.order.restClient;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Configuration
public class ProductServiceClientConfig {

    @Bean
    @LoadBalanced
    public RestClient.Builder loadProductRestClientBuilder(){
        return RestClient.builder();
    }

    @Bean
    public RestClient productRestClientInterface(@Qualifier("loadProductRestClientBuilder") RestClient.Builder builder){
        return builder.baseUrl("http://product-microservice")
                .defaultStatusHandler(HttpStatusCode::is4xxClientError,((request,response)-> Optional.empty()))
                .build();
    }
}
