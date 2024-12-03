package com.spring_cloud.eureka.client.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {

    private Long orderId;  // 주문 ID
    private List<ProductResponseDto> products;  // 주문한 상품 ID 목록
    private int totalPrice;
}
