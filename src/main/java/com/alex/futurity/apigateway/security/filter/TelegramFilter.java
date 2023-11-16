package com.alex.futurity.apigateway.security.filter;


import com.alex.futurity.apigateway.client.UserClient;
import com.alex.futurity.apigateway.dto.UserInfo;
import com.alex.futurity.apigateway.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TelegramFilter extends UserBaseFilter {
    private final UserClient userClient;

    private static final String HAS_TELEGRAM_HEADER_NAME = "telegram";
    private static final List<String> SUPPORTED_ENDPOINTS = List.of("project");

    public TelegramFilter(@Lazy UserClient userClient, JwtService jwtService) {
        super(jwtService);
        this.userClient = userClient;
    }

    @Override
    protected ServerHttpRequest process(ServerHttpRequest request) {
        Long id = getUserId(request);
        UserInfo user = userClient.findUser(id);

        return addHeader(HAS_TELEGRAM_HEADER_NAME, Boolean.toString(user.isHasTelegram()), request);
    }

    @Override
    protected List<String> supportedEndpoints() {
        return SUPPORTED_ENDPOINTS;
    }
}
