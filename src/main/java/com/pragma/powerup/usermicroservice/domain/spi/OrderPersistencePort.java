package com.pragma.powerup.usermicroservice.domain.spi;

import com.pragma.powerup.usermicroservice.domain.model.OrderModel;

import java.util.List;

public interface OrderPersistencePort {

    void createOrder(OrderModel orderModel);
    List<OrderModel> listOrder(int page, int size, Long id, String status);
    boolean existsOrderByCustomer(Long id);
}
