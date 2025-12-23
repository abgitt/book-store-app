package com.sivalabs.bookstore.orders.clients.catalog;

import com.sivalabs.bookstore.orders.ApplicationProperties;
import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
class CatalogServiceClientConfig {
    @Bean
    RestClient restClient(ApplicationProperties properties) {
        return RestClient.builder()
                .baseUrl(properties.catalogServiceUrl())
                .requestFactory(clientHttpRequestFactory())
                .build();
    }

    @Bean
    ClientHttpRequestFactory clientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofSeconds(5)); // Connection timeout
        factory.setReadTimeout(Duration.ofSeconds(5)); // Read timeout
        return factory;
    }
}
