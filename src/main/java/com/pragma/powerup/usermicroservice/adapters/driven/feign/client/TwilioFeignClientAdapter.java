package com.pragma.powerup.usermicroservice.adapters.driven.feign.client;

import com.pragma.powerup.usermicroservice.adapters.driven.feign.dto.TwilioRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "messaging-microservice", url = "localhost:8092")
public interface TwilioFeignClientAdapter {

    @PostMapping("/twilio/message")
    ResponseEntity<Map<String, String>> sendMessage(@RequestBody TwilioRequestDto twilioRequest);

}
