package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.OrderRequestDto;

public interface OrderHandler {

    void createOrder(OrderRequestDto orderRequest);

}
