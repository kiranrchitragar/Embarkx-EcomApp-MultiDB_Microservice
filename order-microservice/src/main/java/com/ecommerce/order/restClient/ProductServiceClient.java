package com.ecommerce.order.restClient;

import com.ecommerce.order.dto.ProductResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ProductServiceClient {

    private final RestClient restClient;

    public ProductServiceClient(@Qualifier("productRestClientInterface") RestClient restClient) {
        this.restClient = restClient;
    }

    public ProductResponse getProductDetailsById(Long id){
        return restClient.get()
                .uri("/api/v1/product/{id}", id) // ✅ Pass id as a parameter
                .retrieve()
                .body(ProductResponse.class);
    }
}
