package com.ecommerce.order.restClient;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.propagation.Propagator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Configuration
public class UserServiceClientConfig {

    @Bean
    public RestClient userRestClientInterface(RestClient.Builder builder){
        return builder.baseUrl("http://user-microservice")
                .defaultStatusHandler(HttpStatusCode::is4xxClientError,((request,response)-> Optional.empty()))
                .build();
    }
}
