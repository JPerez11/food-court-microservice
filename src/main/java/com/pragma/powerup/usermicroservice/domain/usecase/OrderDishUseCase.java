package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.domain.api.OrderDishServicePort;
import com.pragma.powerup.usermicroservice.domain.exceptions.DishNotFoundException;
import com.pragma.powerup.usermicroservice.domain.exceptions.OrderNotBelongCustomerException;
import com.pragma.powerup.usermicroservice.domain.exceptions.OrderNotFoundException;
import com.pragma.powerup.usermicroservice.domain.exceptions.OrderReceivesNoMoreDishesException;
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
        if (orderDishModel == null) {
            throw new NullPointerException();
        }
        Long idOrder = orderDishModel.getOrderModel().getId();
        if (!orderDishPersistencePort.existsOrderById(idOrder)) {
            throw new OrderNotFoundException();
        }
        Long idUser = orderDishPersistencePort.getAuthenticatedUserId();
        if (!orderDishPersistencePort.existsOrderByOrderIdAndCustomerId(idOrder, idUser)) {
            throw new OrderNotBelongCustomerException();
        }
        if (!orderDishPersistencePort.existsOrderByOrderIdAndCustomerIdAndStatus(idOrder, idUser)) {
            throw new OrderReceivesNoMoreDishesException();
        }
        if (!orderDishPersistencePort.existsDishById(orderDishModel.getDishModel().getId())) {
            throw new DishNotFoundException();
        }
        orderDishPersistencePort.createOrderDish(orderDishModel);
    }

    @Override
    public void createAllOrderDishes(List<OrderDishModel> orderDishList) {
        orderDishPersistencePort.createAllOrderDishes(orderDishList);
    }

    @Override
    public List<OrderDishModel> listOrderDish(int page, int size, Long id, String status) {
        return orderDishPersistencePort.listOrderDish(page, size, id, status);
    }
}
