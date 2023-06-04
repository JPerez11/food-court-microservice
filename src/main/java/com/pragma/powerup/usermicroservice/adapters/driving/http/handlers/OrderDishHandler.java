package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.OrderDishRequestDto;

public interface OrderDishHandler {

    void createOrderDish(OrderDishRequestDto orderDishRequest);

}
