package com.msa_project.eureka.client.gateway;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@Component
public class CustomPostFilter implements GlobalFilter, Ordered {

    private static final Logger logger = Logger.getLogger(CustomPostFilter.class.getName());

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            ServerHttpResponse response = exchange.getResponse();

            // 응답 상태 코드 로깅
            logger.info("Post Filter: 응답 상태 코드 " + response.getStatusCode());

            String serverPort = exchange.getRequest().getLocalAddress().getPort() + "";
            response.getHeaders().add("Server-Port", serverPort);

        }));
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;  // 마지막에 실행되도록 설정
    }
}
