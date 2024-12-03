package com.spring_cloud.eureka.client.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {
    private Long productId;
    private String name;
    private int price;
}
