package com.ecommerce.order.restClient;

import com.ecommerce.order.dto.ProductResponse;
import com.ecommerce.order.dto.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class UserServiceClient {

    private final RestClient restClient;

    public UserServiceClient(@Qualifier("userRestClientInterface") RestClient restClient) {
        this.restClient = restClient;
    }

    public UserResponse getUserDetailsById(String id){
        return restClient.get()
                .uri("/api/v1/user/{id}", id) // ✅ Pass id as a parameter
                .retrieve()
                .body(UserResponse.class);
    }
}
