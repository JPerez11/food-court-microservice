package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.RestaurantEmployeeRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.RestaurantEmployeeHandler;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.RestaurantEmployeeRequestMapper;
import com.pragma.powerup.usermicroservice.domain.api.RestaurantEmployeeServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantEmployeeHandlerImpl implements RestaurantEmployeeHandler {

    private final RestaurantEmployeeServicePort servicePort;
    private final RestaurantEmployeeRequestMapper requestMapper;

    @Override
    public void assignEmployee(RestaurantEmployeeRequestDto requestDto) {
        servicePort.assignEmployee(requestMapper.toModel(requestDto));
    }

}
