package com.pragma.powerup.usermicroservice.domain.api;

import com.pragma.powerup.usermicroservice.domain.models.OrderModel;

import java.util.List;

public interface OrderServicePort {

    void createOrder(Long restaurantId);
    void assignEmployee(Long id);
    void updateOrderStatus(Long idOrder, String status);
    void cancelOrder(Long orderId);
    List<OrderModel> showOrderTime();

}
