package com.spring_cloud.eureka.client.order;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderResponseDto {

    private Long orderId;  // 주문 ID
    private List<ProductResponseDto> products;  // 주문한 상품 ID 목록
    private int totalPrice;

    @Builder
    public OrderResponseDto(Long orderId, List<ProductResponseDto> products, int totalPrice) {
        this.orderId = orderId;
        this.products = products;
        this.totalPrice = totalPrice;
    }
}
