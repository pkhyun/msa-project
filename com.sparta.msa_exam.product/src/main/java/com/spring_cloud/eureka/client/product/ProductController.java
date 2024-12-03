package com.spring_cloud.eureka.client.product;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody CreateProductRequestDto requestDto) {
        productService.createProduct(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Server-Port", getServicePort())  // 응답에 Server-Port 헤더 추가
                .body("상품 생성 성공");
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponseDto>> getProducts(
            @ModelAttribute SearchRequestDto requestDto) {
        Page<ProductResponseDto> productPage = productService.getProducts(requestDto);
        return ResponseEntity.ok()
                .header("Server-Port", getServicePort())  // 응답에 Server-Port 헤더 추가
                .body(productPage);
    }

    private String getServicePort() {
        return String.valueOf(System.getProperty("server.port"));
    }
}
