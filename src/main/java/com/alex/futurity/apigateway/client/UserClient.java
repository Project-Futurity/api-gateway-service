package com.alex.futurity.apigateway.client;

import com.alex.futurity.apigateway.dto.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service", url = "${user-server}")
public interface UserClient {
    @GetMapping("/user")
    UserInfo findUser(@RequestHeader("user_id") Long id);
}
