package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.OrderHandler;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.OrderRequestMapper;
import com.pragma.powerup.usermicroservice.domain.api.OrderServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderHandlerImpl implements OrderHandler {

    private final OrderServicePort orderServicePort;
    private final OrderRequestMapper orderRequestMapper;

    @Override
    public void createOrder(OrderRequestDto orderRequest) {
        orderServicePort.createOrder(orderRequestMapper.toOrderModel(orderRequest));
    }
}
