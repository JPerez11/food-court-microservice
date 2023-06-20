package com.pragma.powerup.usermicroservice.domain.spi;

import com.pragma.powerup.usermicroservice.domain.models.OrderModel;

import java.util.List;

public interface OrderPersistencePort {

    void createOrder(OrderModel orderModel);
    List<OrderModel> findOrderByCustomerIdAndRestaurantId(Long customerId, Long restaurantId);
    boolean existsOrderById(Long id);
    Long getAuthenticatedUserId();
    void updateOrder(OrderModel orderModel);
    OrderModel getOrderById(Long id);
    boolean existsRestaurantById(Long restaurantId);
    void cancelOrder(Long orderId);
    List<OrderModel> findOrderByCustomerId(Long customerId);
    List<OrderModel> showOrderTime(Long ownerId);
}
