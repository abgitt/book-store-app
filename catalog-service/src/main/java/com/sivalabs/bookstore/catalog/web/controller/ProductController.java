package com.sivalabs.bookstore.catalog.web.controller;

import com.sivalabs.bookstore.catalog.ApplicationProperties;
import com.sivalabs.bookstore.catalog.domain.PagedResult;
import com.sivalabs.bookstore.catalog.domain.Product;
import com.sivalabs.bookstore.catalog.domain.ProductNotFoundException;
import com.sivalabs.bookstore.catalog.domain.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
class ProductController {

    private final ProductService productService;
    private final ApplicationProperties applicationProperties;

    ProductController(ProductService productService, ApplicationProperties applicationProperties) {
        this.productService = productService;
        this.applicationProperties = applicationProperties;
    }

    @GetMapping
    PagedResult<Product> getProducts(@RequestParam(name = "size", defaultValue = "1") int pageNum) {
        return productService.getProducts(pageNum);
    }

    @GetMapping("/{code}")
    ResponseEntity<Product> findByProductId(@PathVariable String code) {
        return productService
                .getProductByCode(code)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> ProductNotFoundException.forCode(code));
    }
}
