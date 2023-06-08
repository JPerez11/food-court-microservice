package com.pragma.powerup.usermicroservice.domain.api;

import com.pragma.powerup.usermicroservice.domain.model.OrderModel;

public interface OrderServicePort {

    void createOrder(OrderModel orderModel);
    void assignEmployee(Long id);
    void updateOrderStatus(Long idOrder, String status);

}
