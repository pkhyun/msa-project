package com.spring_cloud.eureka.client.order;

import lombok.Getter;

import java.util.List;

@Getter
public class CreateOrderRequestDto {
    private List<Long> productIds;
}
