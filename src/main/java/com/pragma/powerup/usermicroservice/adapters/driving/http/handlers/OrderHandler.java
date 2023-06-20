package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.OrderTimeResponseDto;

import java.util.List;

public interface OrderHandler {

    void createOrder(Long restaurantId);
    void assignEmployee(Long id);
    void updateOrderStatus(Long id, String status);
    void cancelOrder(Long orderId);
    List<OrderTimeResponseDto> showOrderTime();

}
