package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.domain.api.OrderDishServicePort;
import com.pragma.powerup.usermicroservice.domain.model.OrderDishModel;
import com.pragma.powerup.usermicroservice.domain.spi.OrderDishPersistencePort;

import java.util.List;

public class OrderDishUseCase implements OrderDishServicePort {

    private final OrderDishPersistencePort orderDishPersistencePort;

    public OrderDishUseCase(OrderDishPersistencePort orderDishPersistencePort) {
        this.orderDishPersistencePort = orderDishPersistencePort;
    }

    @Override
    public void createOrderDish(OrderDishModel orderDishModel) {
        orderDishPersistencePort.createOrderDish(orderDishModel);
    }

    @Override
    public List<OrderDishModel> listOrderDish(int page, int size, Long id, String status) {
        return orderDishPersistencePort.listOrderDish(page, size, id, status);
    }
}
