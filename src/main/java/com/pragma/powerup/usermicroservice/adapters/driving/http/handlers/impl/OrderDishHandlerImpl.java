package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.OrderDishRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.OrderDishHandler;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.OrderDishRequestMapper;
import com.pragma.powerup.usermicroservice.domain.api.OrderDishServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderDishHandlerImpl implements OrderDishHandler {

    private final OrderDishServicePort orderDishServicePort;
    private final OrderDishRequestMapper orderDishRequestMapper;

    @Override
    public void createOrderDish(OrderDishRequestDto orderDishRequest) {
        orderDishServicePort.createOrderDish(
                orderDishRequestMapper.toOrderDishModel(orderDishRequest));
    }
}
