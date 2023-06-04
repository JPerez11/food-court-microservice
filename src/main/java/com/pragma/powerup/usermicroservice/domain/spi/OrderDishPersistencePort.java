package com.pragma.powerup.usermicroservice.domain.spi;

import com.pragma.powerup.usermicroservice.domain.model.OrderDishModel;

import java.util.List;

public interface OrderDishPersistencePort {

    void createOrderDish(OrderDishModel orderDishModel);
    void createAllOrderDishes(List<OrderDishModel> orderDishList);
    List<OrderDishModel> listOrderDish(int page, int size, Long id, String status);
    Long getAuthenticatedUserId();
    boolean existsOrderById(Long idOrder);
    boolean existsOrderByOrderIdAndCustomerId(Long idOrder, Long idCustomer);
    boolean existsOrderByOrderIdAndCustomerIdAndStatus(Long idOrder, Long idCustomer);
    boolean existsDishById(Long idDish);

}
