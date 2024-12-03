package com.spring_cloud.eureka.client.order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequestDto requestDto) {
        try {
            OrderResponseDto responseDto = orderService.createOrder(requestDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .header("Server-Port", getServicePort())
                    .body(responseDto);
        } catch (IllegalAccessError e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .header("Server-Port", getServicePort())
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<?> addProductToOrder(@PathVariable Long orderId, @RequestBody AddProductRequestDto requestDto) {
        try {
            OrderResponseDto responseDto = orderService.addProductToOrder(orderId, requestDto.getProductId());
            return ResponseEntity.ok(responseDto);
        } catch (IllegalAccessError e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header("Server-Port", getServicePort())
                    .body(e.getMessage());
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable Long orderId) {
        try {
            OrderResponseDto responseDto = orderService.getOrder(orderId);
            return ResponseEntity.ok(responseDto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("Server-Port", getServicePort())
                    .body(e.getMessage());
        }
    }


    private String getServicePort() {
        return String.valueOf(System.getProperty("server.port"));
    }
}
