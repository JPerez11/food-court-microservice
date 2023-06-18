package com.pragma.powerup.usermicroservice.domain.api;

public interface OrderServicePort {

    void createOrder(Long restaurantId);
    void assignEmployee(Long id);
    void updateOrderStatus(Long idOrder, String status);
    void cancelOrder(Long orderId);

}
