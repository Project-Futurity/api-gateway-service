package com.alex.futurity.apigateway.config;

import com.alex.futurity.apigateway.security.filter.JwtFilter;
import com.alex.futurity.apigateway.security.filter.UserBaseFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class GatewayConfig {
    @Value("${authorization-server}")
    private String authorizationServer;
    @Value("${user-server}")
    private String userServer;
    @Value("${project-server}")
    private String projectServer;

    private final JwtFilter jwtFilter;
    private final List<GatewayFilter> userFilters;

    public GatewayConfig(JwtFilter jwtFilter, List<UserBaseFilter> userFilters) {
        this.jwtFilter = jwtFilter;
        this.userFilters = userFilters.stream()
                .map(GatewayFilter.class::cast)
                .toList();
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/auth/**")
                        .filters(rw -> rw.rewritePath("/auth/(?<segment>.*)", "/${segment}"))
                        .uri(authorizationServer))
                .route(r -> r.path("/user/**")
                        .filters(f -> f.filter(jwtFilter)
                                .filters(userFilters)
                                .rewritePath("/user/(?<segment>.*)", "/${segment}"))
                        .uri(userServer))
                .route(r -> r.path("/project/**")
                        .filters(f -> f.filter(jwtFilter)
                                .filters(userFilters)
                                .rewritePath("/project/(?<segment>.*)", "/${segment}"))
                        .uri(projectServer))
                .build();
    }
}
