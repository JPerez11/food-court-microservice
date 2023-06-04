package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.configuration.utils.Constants;
import com.pragma.powerup.usermicroservice.domain.api.OrderServicePort;
import com.pragma.powerup.usermicroservice.domain.exceptions.OrderAlreadyExistsException;
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
        if (orderModel == null) {
            throw new NullPointerException();
        }
        if (orderPersistencePort.existsOrderByCustomer(orderModel.getIdCustomer())) {
            throw new OrderAlreadyExistsException();
        }
        orderModel.setStatus(Constants.PENDING_STATUS);
        orderPersistencePort.createOrder(orderModel);
    }

    @Override
    public List<OrderModel> listOrder(int page, int size, Long id, String status) {
        return orderPersistencePort.listOrder(page, size, id, status);
    }
}
