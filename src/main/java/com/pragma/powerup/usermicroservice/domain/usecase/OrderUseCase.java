package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.configuration.utils.Constants;
import com.pragma.powerup.usermicroservice.domain.api.OrderServicePort;
import com.pragma.powerup.usermicroservice.domain.exceptions.OrderAlreadyExistsException;
import com.pragma.powerup.usermicroservice.domain.exceptions.OrderNotAssignEmployeeException;
import com.pragma.powerup.usermicroservice.domain.exceptions.OrderNotFoundException;
import com.pragma.powerup.usermicroservice.domain.exceptions.RestaurantNotFoundException;
import com.pragma.powerup.usermicroservice.domain.exceptions.StatusNotModifiedException;
import com.pragma.powerup.usermicroservice.domain.exceptions.UserNotFoundException;
import com.pragma.powerup.usermicroservice.domain.fpi.TwilioFeignClientPort;
import com.pragma.powerup.usermicroservice.domain.fpi.UserFeignClientPort;
import com.pragma.powerup.usermicroservice.domain.model.OrderModel;
import com.pragma.powerup.usermicroservice.domain.model.RestaurantModel;
import com.pragma.powerup.usermicroservice.domain.model.TwilioModel;
import com.pragma.powerup.usermicroservice.domain.model.UserModel;
import com.pragma.powerup.usermicroservice.domain.spi.OrderPersistencePort;

import java.time.LocalDateTime;
import java.util.Objects;

public class OrderUseCase implements OrderServicePort {

    private final OrderPersistencePort orderPersistencePort;
    private final UserFeignClientPort userFeignClientPort;
    private final TwilioFeignClientPort twilioFeignClientPort;

    public OrderUseCase(OrderPersistencePort orderPersistencePort, UserFeignClientPort userFeignClientPort, TwilioFeignClientPort twilioFeignClientPort) {
        this.orderPersistencePort = orderPersistencePort;
        this.userFeignClientPort = userFeignClientPort;
        this.twilioFeignClientPort = twilioFeignClientPort;
    }

    @Override
    public void createOrder(Long restaurantId) {
        if (restaurantId == null) {
            throw new NullPointerException();
        }
        Long customerId = orderPersistencePort.getAuthenticatedUserId();
        if (orderPersistencePort.existsOrderByCustomer(customerId)) {
            throw new OrderAlreadyExistsException();
        }
        if (!orderPersistencePort.existsRestaurantById(restaurantId)) {
            throw new RestaurantNotFoundException();
        }
        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setId(restaurantId);
        OrderModel orderModel = new OrderModel();
        orderModel.setIdCustomer(customerId);
        orderModel.setDate(LocalDateTime.now());
        orderModel.setStatus(Constants.PENDING_STATUS);
        orderModel.setRestaurantModel(restaurantModel);
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
        orderDb.setStatus(Constants.PREPARING_STATUS);
        orderPersistencePort.updateOrder(orderDb);
    }

    @Override
    public void updateOrderStatus(Long idOrder, String status) {
        if (!orderPersistencePort.existsOrderById(idOrder)) {
            throw new OrderNotFoundException();
        }
        if (status.equalsIgnoreCase(Constants.DELIVERED_STATUS)) {
            throw new StatusNotModifiedException();
        }
        OrderModel orderDb = orderPersistencePort.getOrderById(idOrder);
        if (orderDb == null) {
            throw new NullPointerException();
        }
        Long idEmployee = orderPersistencePort.getAuthenticatedUserId();
        if (!Objects.equals(orderDb.getIdEmployee(), idEmployee)) {
            throw new OrderNotAssignEmployeeException();
        }

        UserModel userModel = userFeignClientPort.getUserById(orderDb.getIdCustomer());
        if (userModel == null) {
            throw new UserNotFoundException();
        }

        if (status.equalsIgnoreCase(Constants.READY_STATUS)) {
            twilioFeignClientPort.sendMessage(
                    new TwilioModel(
                    Constants.NOTIFICATION_MESSAGE,
                    userModel.getPhoneNumber())
            );
        }

        orderDb.setStatus(status.toUpperCase());
        orderPersistencePort.updateOrder(orderDb);
    }

}
