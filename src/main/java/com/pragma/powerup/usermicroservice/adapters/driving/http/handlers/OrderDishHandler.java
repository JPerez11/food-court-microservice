package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.OrderDishRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.OrderResponseDto;

import java.util.List;

public interface OrderDishHandler {

    void createOrderDish(OrderDishRequestDto orderDishRequest);

    List<OrderResponseDto> getOrdersByStatus(int page, int size, String status);

}
