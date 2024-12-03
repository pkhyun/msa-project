package com.spring_cloud.eureka.client.product;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(CreateProductRequestDto requestDto) {
        Product product = Product.builder()
                .name(requestDto.getName())
                .price(requestDto.getPrice())
                .build();

        productRepository.save(product);
    }

    public Page<ProductResponseDto> getProducts(SearchRequestDto requestDto) {
        Pageable pageable = PageRequest.of(requestDto.getPageNumber(), 10,
                requestDto.isAsc() ? Sort.by(Sort.Order.asc("price")) : Sort.by(Sort.Order.desc("price")));

        Page<Product> productPage = productRepository.searchProducts(requestDto.getKeyword(), pageable);

        return productPage.map(product -> ProductResponseDto.builder()
                .productId(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .build());
    }

    public ProductResponseDto getProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 상품을 찾을 수 없습니다."));
        return ProductResponseDto.builder()
                .name(product.getName())
                .productId(product.getId())
                .price(product.getPrice())
                .build();
    }
}
