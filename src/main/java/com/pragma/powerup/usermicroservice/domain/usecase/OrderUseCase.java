package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.configuration.utils.Constants;
import com.pragma.powerup.usermicroservice.domain.api.OrderServicePort;
import com.pragma.powerup.usermicroservice.domain.exceptions.OrderAlreadyExistsException;
import com.pragma.powerup.usermicroservice.domain.exceptions.OrderNotAssignEmployeeException;
import com.pragma.powerup.usermicroservice.domain.exceptions.OrderNotFoundException;
import com.pragma.powerup.usermicroservice.domain.model.OrderModel;
import com.pragma.powerup.usermicroservice.domain.spi.OrderPersistencePort;

import java.util.Objects;

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
    public void assignEmployee(Long id) {
        if (!orderPersistencePort.existsOrderById(id)) {
            throw new OrderNotFoundException();
        }
        OrderModel orderDb = orderPersistencePort.getOrderById(id);
        if (orderDb == null) {
            throw new NullPointerException();
        }
        Long idEmployee = orderPersistencePort.getAuthenticatedUserId();
        orderDb.setIdEmployee(idEmployee);
        orderPersistencePort.updateOrder(orderDb);
    }

    @Override
    public void updateOrderStatus(Long idOrder, String status) {
        if (!orderPersistencePort.existsOrderById(idOrder)) {
            throw new OrderNotFoundException();
        }
        OrderModel orderDb = orderPersistencePort.getOrderById(idOrder);
        if (orderDb == null) {
            throw new NullPointerException();
        }
        Long idEmployee = orderPersistencePort.getAuthenticatedUserId();
        if (Objects.equals(orderDb.getIdEmployee(), idEmployee)) {
            throw new OrderNotAssignEmployeeException();
        }
        orderDb.setStatus(status.toUpperCase());

        orderPersistencePort.updateOrder(orderDb);
    }

}
