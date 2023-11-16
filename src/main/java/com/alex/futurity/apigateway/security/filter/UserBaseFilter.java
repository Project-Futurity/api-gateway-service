package com.alex.futurity.apigateway.security.filter;

import com.alex.futurity.apigateway.security.AuthorizationHeaderHandler;
import com.alex.futurity.apigateway.security.JwtService;
import com.alex.futurity.apigateway.security.RouterValidator;
import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@AllArgsConstructor
public abstract class UserBaseFilter implements GatewayFilter {
    protected final JwtService jwtService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (this.support(request)) {
            ServerHttpRequest processedRequest = process(request);

            return chain.filter(exchange.mutate()
                    .request(processedRequest)
                    .build());
        }

        return chain.filter(exchange);
    }

    protected ServerHttpRequest addHeader(String headerName, String value, ServerHttpRequest request) {
        return request.mutate()
                .header(headerName, value)
                .build();
    }

    protected Long getUserId(ServerHttpRequest request) {
        String token = AuthorizationHeaderHandler.getTokenFromHeader(request);

        return jwtService.getUserIdFromToken(token);
    }

    protected abstract ServerHttpRequest process(ServerHttpRequest request);

    protected abstract List<String> supportedEndpoints();

    private boolean support(ServerHttpRequest request) {
        return RouterValidator.supports(request, supportedEndpoints());
    }
}
