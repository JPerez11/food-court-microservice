package com.pragma.powerup.usermicroservice.domain.spi;

import com.pragma.powerup.usermicroservice.domain.model.OrderDishModel;
import com.pragma.powerup.usermicroservice.domain.model.OrderModel;

import java.util.List;

public interface OrderDishPersistencePort {

    void createOrderDish(OrderDishModel orderDishModel);
    void createAllOrderDishes(List<OrderDishModel> orderDishList);
    List<OrderDishModel> listOrderDish(int page, int size, Long employeeId, Long restaurantId, String status);
    Long getAuthenticatedUserId();
    boolean existsOrderById(Long idOrder);
    boolean existsOrderByOrderIdAndCustomerId(Long idOrder, Long idCustomer);
    boolean existsOrderByOrderIdAndCustomerIdAndStatus(Long idOrder, Long idCustomer);
    boolean existsDishById(Long idDish);
    boolean existsDishByIdAndRestaurantId(Long idDish, Long idRestaurant);
    boolean existsOrderByEmployeeId(Long idEmployee);
    Long findRestaurantEmployee(Long employeeId);
    OrderModel findOrderById(Long idOrder);

}
