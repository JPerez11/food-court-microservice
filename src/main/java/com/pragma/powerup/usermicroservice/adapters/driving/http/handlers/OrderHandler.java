package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers;

public interface OrderHandler {

    void createOrder(Long restaurantId);
    void assignEmployee(Long id);
    void updateOrderStatus(Long id, String status);

}
