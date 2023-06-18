package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.OrderHandler;
import com.pragma.powerup.usermicroservice.domain.api.OrderServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderHandlerImpl implements OrderHandler {

    private final OrderServicePort orderServicePort;

    @Override
    public void createOrder(Long restaurantId) {
        orderServicePort.createOrder(restaurantId);
    }

    @Override
    public void assignEmployee(Long id) {
        orderServicePort.assignEmployee(id);
    }

    @Override
    public void updateOrderStatus(Long id, String status) {
        orderServicePort.updateOrderStatus(id, status);
    }

    @Override
    public void cancelOrder(Long orderId) {
        orderServicePort.cancelOrder(orderId);
    }
}
