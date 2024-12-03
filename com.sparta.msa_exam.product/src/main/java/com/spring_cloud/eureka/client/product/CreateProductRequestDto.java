package com.spring_cloud.eureka.client.product;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateProductRequestDto {
    @NotNull(message = "상품 이름은 필수 입력값입니다.")
    private String name;
    @NotNull(message = "상품 가격은 필수 입력값입니다")
    private int price;

    @Builder
    public CreateProductRequestDto(String name, int price) {
        this.name = name;
        this.price = price;
    }
}
