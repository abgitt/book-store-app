package com.sivalabs.bookstore.orders.clients.catalog;

import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ProductServiceClient {

    private final RestClient restClient;

    public ProductServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public Optional<Product> getProductByCode(String code) {
        try{  // uncomment this code to get the correct error code. when catalog service is down, generic exception will thrownhere. But when we are handling with try catch , it will handled by caller ( in our case in validator we are havingInvalidOrderException

        Product product =
                restClient.get().uri("/api/products/{code}", code).retrieve().body(Product.class);
        return Optional.ofNullable(product);}
        catch (Exception e)
        {
            return Optional.empty();
        }
    }
}
