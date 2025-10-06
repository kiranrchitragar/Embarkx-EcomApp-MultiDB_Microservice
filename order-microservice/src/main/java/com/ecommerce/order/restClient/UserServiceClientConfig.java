package com.ecommerce.order.restClient;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Configuration
public class UserServiceClientConfig {

    @Bean
    @LoadBalanced
    public RestClient.Builder loadUserRestClientBuilder(){
        return RestClient.builder();
    }

    @Bean
    public RestClient userRestClientInterface(@Qualifier("loadUserRestClientBuilder") RestClient.Builder builder){
        return builder.baseUrl("http://user-microservice")
                .defaultStatusHandler(HttpStatusCode::is4xxClientError,((request,response)-> Optional.empty()))
                .build();
    }
}
