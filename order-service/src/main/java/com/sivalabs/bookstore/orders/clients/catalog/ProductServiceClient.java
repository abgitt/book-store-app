package com.sivalabs.bookstore.orders.clients.catalog;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ProductServiceClient {
    private static final Logger log = LoggerFactory.getLogger(ProductServiceClient.class);

    private final RestClient restClient;

    public ProductServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @Retry(name = "catalog-service", fallbackMethod = "getProductByCodeFallBack")
    @CircuitBreaker(name = "catalog-service")
    public Optional<Product> getProductByCode(String code) {
        // try{  // uncomment this code to get the correct error code. when catalog service is down, generic exception
        // will thrownhere. But when we are handling with try catch , it will handled by caller ( in our case in
        // validator we are havingInvalidOrderException
        log.info("Fetching product for code: {}", code);

        Product product =
                restClient.get().uri("/api/products/{code}", code).retrieve().body(Product.class);
        return Optional.ofNullable(product); /*}
        catch (Exception e)
        {
            return Optional.empty();
        }*/
    }

    public Optional<Product> getProductByCodeFallBack(String code, Throwable t) {
        log.info("ProductServiceClient.getProductByCodeFallBack for code: {}", code);
        return Optional.empty();
    }
}
