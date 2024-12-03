package com.spring_cloud.eureka.client.order;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    public ProductResponseDto getProductWithFallback(Long productId) {
        try {
            return productClient.getProduct(productId);
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public OrderResponseDto createOrder(CreateOrderRequestDto requestDto) {
        List<ProductResponseDto> responseDtoList = requestDto.getProductIds().stream()
                .map(this::getProductWithFallback)  // 상품 정보를 Fallback 처리 포함 조회
                .toList();

        if (responseDtoList.contains(null)) {
            throw new IllegalArgumentException("잠시 후에 주문 추가를 요청 해주세요.");
        }

        int totalPrice = responseDtoList.stream()
                .mapToInt(ProductResponseDto::getPrice)
                .sum();

        Order order = Order.builder()
                .productIds(requestDto.getProductIds())
                .build();
        orderRepository.save(order);

        return OrderResponseDto.builder()
                .orderId(order.getId())
                .products(responseDtoList)
                .totalPrice(totalPrice)
                .build();
    }

    @Transactional
    public OrderResponseDto addProductToOrder(Long orderId, Long productId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));

        ProductResponseDto product = getProductWithFallback(productId);
        if (product == null) {
            throw new IllegalArgumentException("잠시 후에 주문에 상품 추가 요청을 해주세요.");
        }

        order.getProductIds().add(productId);
        orderRepository.save(order);

        List<ProductResponseDto> updatedProducts = order.getProductIds().stream()
                .map(this::getProductWithFallback)
                .toList();

        int totalPrice = updatedProducts.stream()
                .mapToInt(ProductResponseDto::getPrice)
                .sum();

        return OrderResponseDto.builder()
                .orderId(order.getId())
                .products(updatedProducts)
                .totalPrice(totalPrice)
                .build();
    }

    @Cacheable(value = "orders", key = "#orderId", unless = "#result == null", cacheManager = "contentCacheManager")
    @Transactional
    public OrderResponseDto getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));

        List<ProductResponseDto> responseDtoList = order.getProductIds().stream()
                .map(this::getProductWithFallback)
                .toList();

        if (responseDtoList.contains(null)) {
            throw new IllegalArgumentException("잠시 후에 다시 요청 해주세요.");
        }

        int totalPrice = responseDtoList.stream()
                .mapToInt(ProductResponseDto::getPrice)
                .sum();

        return OrderResponseDto.builder()
                .orderId(order.getId())
                .products(responseDtoList)
                .totalPrice(totalPrice)
                .build();
    }

}
