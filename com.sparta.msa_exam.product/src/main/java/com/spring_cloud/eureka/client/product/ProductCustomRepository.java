package com.spring_cloud.eureka.client.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductCustomRepository {
    Page<Product> searchProducts(String keyword, Pageable pageable);
}
