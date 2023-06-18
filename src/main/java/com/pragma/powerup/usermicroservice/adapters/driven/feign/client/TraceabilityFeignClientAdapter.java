package com.pragma.powerup.usermicroservice.adapters.driven.feign.client;

import com.pragma.powerup.usermicroservice.adapters.driven.feign.dto.TraceabilityDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "traceability-microservice", url = "localhost:8093")
public interface TraceabilityFeignClientAdapter {

    @PostMapping("/traceability/")
    ResponseEntity<Map<String, String>> saveHistory(@RequestBody TraceabilityDto traceabilityDto);

    @GetMapping("/traceability/{id}")
    ResponseEntity<List<TraceabilityDto>> getHistory(@PathVariable Long id);

}
