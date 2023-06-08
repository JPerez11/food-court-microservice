package com.pragma.powerup.usermicroservice.domain.spi;

import com.pragma.powerup.usermicroservice.domain.model.OrderModel;

public interface OrderPersistencePort {

    void createOrder(OrderModel orderModel);
    boolean existsOrderByCustomer(Long id);
    boolean existsOrderById(Long id);
    Long getAuthenticatedUserId();
    void updateOrder(OrderModel orderModel);
    OrderModel getOrderById(Long id);
}
