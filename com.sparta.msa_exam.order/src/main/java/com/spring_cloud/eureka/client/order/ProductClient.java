package com.spring_cloud.eureka.client.order;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", fallback = ProductClientFallback.class)
public interface ProductClient {
    @GetMapping("/products/{productId}")
    ProductResponseDto getProduct(@PathVariable("productId") Long productId);
}

class ProductClientFallback implements ProductClient {
    @Override
    public ProductResponseDto getProduct(Long productId) {
        return null;
    }
}
