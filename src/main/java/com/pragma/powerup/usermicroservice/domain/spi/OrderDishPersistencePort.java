package com.pragma.powerup.usermicroservice.domain.spi;

import com.pragma.powerup.usermicroservice.domain.model.OrderDishModel;

import java.util.List;

public interface OrderDishPersistencePort {

    void createOrderDish(OrderDishModel orderDishModel);
    List<OrderDishModel> listOrderDish(int page, int size, Long id, String status);

}
