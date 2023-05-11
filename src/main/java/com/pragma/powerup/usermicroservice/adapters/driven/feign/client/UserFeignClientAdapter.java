package com.pragma.powerup.usermicroservice.adapters.driven.feign.client;

import com.pragma.powerup.usermicroservice.adapters.driven.feign.dto.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-microservice", url = "localhost:8090")
public interface UserFeignClientAdapter {

    @GetMapping("/user/{id}")
    ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id);

}
