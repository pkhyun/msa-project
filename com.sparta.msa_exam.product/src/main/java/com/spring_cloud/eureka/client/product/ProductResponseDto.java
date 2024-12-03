package com.spring_cloud.eureka.client.product;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductResponseDto {
    private Long productId;
    private String name;
    private int price;

    @Builder
    public ProductResponseDto(Long productId, String name, int price) {
        this.productId = productId;
        this.name = name;
        this.price = price;
    }
}
