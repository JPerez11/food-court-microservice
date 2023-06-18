package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.configuration.utils.Constants;
import com.pragma.powerup.usermicroservice.domain.api.OrderServicePort;
import com.pragma.powerup.usermicroservice.domain.exceptions.OrderAlreadyExistsException;
import com.pragma.powerup.usermicroservice.domain.exceptions.OrderNotAssignEmployeeException;
import com.pragma.powerup.usermicroservice.domain.exceptions.OrderNotBelongCustomerException;
import com.pragma.powerup.usermicroservice.domain.exceptions.OrderNotFoundException;
import com.pragma.powerup.usermicroservice.domain.exceptions.OrderCannotBeCanceledException;
import com.pragma.powerup.usermicroservice.domain.exceptions.OrderStatusCannotChangedException;
import com.pragma.powerup.usermicroservice.domain.exceptions.RestaurantNotFoundException;
import com.pragma.powerup.usermicroservice.domain.exceptions.StatusInvalidException;
import com.pragma.powerup.usermicroservice.domain.exceptions.UserNotFoundException;
import com.pragma.powerup.usermicroservice.domain.fpi.TwilioFeignClientPort;
import com.pragma.powerup.usermicroservice.domain.fpi.UserFeignClientPort;
import com.pragma.powerup.usermicroservice.domain.model.OrderModel;
import com.pragma.powerup.usermicroservice.domain.model.RestaurantModel;
import com.pragma.powerup.usermicroservice.domain.model.TwilioModel;
import com.pragma.powerup.usermicroservice.domain.model.UserModel;
import com.pragma.powerup.usermicroservice.domain.spi.OrderPersistencePort;

import java.time.LocalDateTime;
import java.util.List;
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
        if (!orderPersistencePort.existsRestaurantById(restaurantId)) {
            throw new RestaurantNotFoundException();
        }
        Long customerId = orderPersistencePort.getAuthenticatedUserId();
        List<OrderModel> orderDb = orderPersistencePort.findOrderByCustomerIdAndRestaurantId(customerId, restaurantId);
        if (!orderDb.isEmpty()) {
            for (OrderModel order :
                    orderDb) {
                if (!(order.getStatus().equalsIgnoreCase(Constants.CANCELED_STATUS) ||
                    order.getStatus().equalsIgnoreCase(Constants.DELIVERED_STATUS))) {
                    throw new OrderAlreadyExistsException();
                }
            }
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
        if (isInvalidStatus(status)) {
            throw new StatusInvalidException();
        }
        if (!orderPersistencePort.existsOrderById(idOrder)) {
            throw new OrderNotFoundException();
        }
        OrderModel orderDb = orderPersistencePort.getOrderById(idOrder);
        if (orderDb == null) {
            throw new NullPointerException();
        }
        if (!orderDb.getStatus().equalsIgnoreCase(Constants.PENDING_STATUS) &&
                status.equalsIgnoreCase(Constants.CANCELED_STATUS)) {
            throw new OrderCannotBeCanceledException();
        }
        Long idEmployee = orderPersistencePort.getAuthenticatedUserId();
        if (!Objects.equals(orderDb.getIdEmployee(), idEmployee)) {
            throw new OrderNotAssignEmployeeException();
        }
        if (orderDb.getStatus().equalsIgnoreCase(Constants.DELIVERED_STATUS) ||
                orderDb.getStatus().equalsIgnoreCase(Constants.CANCELED_STATUS)) {
            throw new OrderStatusCannotChangedException();
        }

        UserModel userModel = userFeignClientPort.getUserById(orderDb.getIdCustomer());
        if (userModel == null) {
            throw new UserNotFoundException();
        }

        if (status.equalsIgnoreCase(Constants.READY_STATUS)) {
            twilioFeignClientPort.sendMessage(
                    new TwilioModel(
                    Constants.ORDER_READY_NOTIFICATION_MESSAGE,
                    userModel.getPhoneNumber())
            );
        }

        if (!orderDb.getStatus().equalsIgnoreCase(Constants.READY_STATUS) &&
                status.equalsIgnoreCase(Constants.DELIVERED_STATUS)) {
            throw new OrderStatusCannotChangedException();
        }

        orderDb.setStatus(status.toUpperCase());
        orderPersistencePort.updateOrder(orderDb);
    }

    @Override
    public void cancelOrder(Long orderId) {
        if (!orderPersistencePort.existsOrderById(orderId)) {
            throw new OrderNotFoundException();
        }
        OrderModel orderDb = orderPersistencePort.getOrderById(orderId);
        if (orderDb == null) {
            throw new NullPointerException();
        }
        Long customerId = orderPersistencePort.getAuthenticatedUserId();
        if (!Objects.equals(orderDb.getIdCustomer(), customerId)) {
            throw new OrderNotBelongCustomerException();
        }
        UserModel userModel = userFeignClientPort.getUserById(customerId);
        if (userModel == null) {
            throw new UserNotFoundException();
        }
        if (!orderDb.getStatus().equalsIgnoreCase(Constants.PENDING_STATUS)) {
            twilioFeignClientPort.sendMessage(new TwilioModel(
                    Constants.ORDER_PREPARING_NOTIFICATION_MESSAGE,
                    userModel.getPhoneNumber())
            );
            throw new OrderCannotBeCanceledException();
        }
        orderPersistencePort.cancelOrder(orderId);
    }

    private boolean isInvalidStatus(String status) {
        return switch (status.toUpperCase()) {
            case Constants.PENDING_STATUS, Constants.PREPARING_STATUS,
                    Constants.READY_STATUS, Constants.DELIVERED_STATUS
                    -> false;
            default -> true;
        };
    }

}
