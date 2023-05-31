package com.pragma.powerup.usermicroservice.domain.api;

import com.pragma.powerup.usermicroservice.domain.model.OrderModel;

import java.util.List;

public interface OrderServicePort {

    void createOrder(OrderModel orderModel);
    List<OrderModel> listOrder(int page, int size, Long id, String status);

}
