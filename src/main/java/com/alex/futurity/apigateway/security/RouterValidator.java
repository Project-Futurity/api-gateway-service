package com.alex.futurity.apigateway.security;

import lombok.experimental.UtilityClass;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.List;

@UtilityClass
public class RouterValidator {
    private static final List<String> ENDPOINTS = List.of(
        "user", "project"
    );

    public boolean isSecured(ServerHttpRequest request) {
        return supports(request, ENDPOINTS);
    }
    public boolean supports(ServerHttpRequest request, List<String> endpoints) {
        String path = request.getURI().getPath();

        return endpoints.stream().anyMatch(path::contains);
    }
}
