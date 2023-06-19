package com.pragma.powerup.usermicroservice.domain.api;

import com.pragma.powerup.usermicroservice.domain.models.OrderDishModel;

import java.util.List;

public interface OrderDishServicePort {

    void createOrderDish(OrderDishModel orderDishModel);
    void createAllOrderDishes(List<OrderDishModel> orderDishList);
    List<OrderDishModel> listOrderDish(int page, int size, String status);

}
