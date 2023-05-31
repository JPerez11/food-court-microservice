package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.domain.api.OrderServicePort;
import com.pragma.powerup.usermicroservice.domain.model.OrderModel;
import com.pragma.powerup.usermicroservice.domain.spi.OrderPersistencePort;

import java.util.List;

public class OrderUseCase implements OrderServicePort {

    private final OrderPersistencePort orderPersistencePort;

    public OrderUseCase(OrderPersistencePort orderPersistencePort) {
        this.orderPersistencePort = orderPersistencePort;
    }

    @Override
    public void createOrder(OrderModel orderModel) {
        orderPersistencePort.createOrder(orderModel);
    }

    @Override
    public List<OrderModel> listOrder(int page, int size, Long id, String status) {
        return orderPersistencePort.listOrder(page, size, id, status);
    }
}
