package com.alex.futurity.apigateway.security.filter;

import com.alex.futurity.apigateway.security.JwtService;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserIdFilter extends UserBaseFilter {
    private static final List<String> ENDPOINTS = List.of("user", "project");
    private static final String USER_ID_HEADER_NAME = "user_id";

    public UserIdFilter(JwtService jwtService) {
        super(jwtService);
    }

    @Override
    protected ServerHttpRequest process(ServerHttpRequest request) {
        Long id = getUserId(request);
        return addHeader(USER_ID_HEADER_NAME, Long.toString(id), request);
    }

    @Override
    protected List<String> supportedEndpoints() {
        return ENDPOINTS;
    }

}
