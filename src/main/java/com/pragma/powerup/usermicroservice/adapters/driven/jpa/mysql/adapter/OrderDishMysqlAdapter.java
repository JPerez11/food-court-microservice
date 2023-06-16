package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.OrderDishEntity;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.OrderDishEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.OrderEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.DishRepository;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.OrderDishRepository;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.OrderRepository;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.RestaurantEmployeeRepository;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.utils.ExtractAuthorization;
import com.pragma.powerup.usermicroservice.configuration.utils.Constants;
import com.pragma.powerup.usermicroservice.domain.model.OrderDishModel;
import com.pragma.powerup.usermicroservice.domain.model.OrderModel;
import com.pragma.powerup.usermicroservice.domain.spi.OrderDishPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
public class OrderDishMysqlAdapter implements OrderDishPersistencePort {

    private final OrderDishRepository orderDishRepository;
    private final OrderRepository orderRepository;
    private final DishRepository dishRepository;
    private final RestaurantEmployeeRepository restaurantEmployeeRepository;
    private final OrderDishEntityMapper orderDishEntityMapper;
    private final OrderEntityMapper orderEntityMapper;

    @Override
    public void createOrderDish(OrderDishModel orderDishModel) {
        orderDishRepository.save(orderDishEntityMapper.toOrderDishEntity(orderDishModel));
    }

    @Override
    public void createAllOrderDishes(List<OrderDishModel> orderDishList) {
        orderDishRepository.saveAll(orderDishEntityMapper.toEntityList(orderDishList));
    }

    @Override
    public List<OrderDishModel> listOrderDish(int page, int size, Long employeeId, Long restaurantId, String status) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderDishEntity> orderDishEntityPage =
                orderDishRepository.findOrderDishWithQuery(
                        employeeId, restaurantId, status, pageable);
        return orderDishEntityMapper.toModelList(orderDishEntityPage.getContent());
    }

    @Override
    public Long getAuthenticatedUserId() {
        return ExtractAuthorization.getAuthenticatedUserId();
    }

    @Override
    public boolean existsOrderById(Long idOrder) {
        return orderRepository.existsById(idOrder);
    }

    @Override
    public boolean existsOrderByOrderIdAndCustomerId(Long idOrder, Long idCustomer) {
        return orderRepository.existsByIdAndIdCustomer(idOrder, idCustomer);
    }

    @Override
    public boolean existsOrderByEmployeeId(Long idEmployee) {
        return orderRepository.existsByIdEmployee(idEmployee);
    }

    @Override
    public Long findRestaurantEmployee(Long employeeId) {
        return restaurantEmployeeRepository.findRestaurantIdByEmployeeId(employeeId);
    }

    @Override
    public OrderModel findOrderById(Long idOrder) {
        return orderEntityMapper.toOrderModel(orderRepository.findById(idOrder).orElse(null));
    }

    @Override
    public boolean existsOrderByOrderIdAndCustomerIdAndStatus(Long idOrder, Long idCustomer) {
        return orderRepository.existsByIdAndIdCustomerAndStatusContainingIgnoreCase(
                idOrder, idCustomer, Constants.PENDING_STATUS);
    }

    @Override
    public boolean existsDishById(Long idDish) {
        return dishRepository.existsById(idDish);
    }

    @Override
    public boolean existsDishByIdAndRestaurantId(Long idDish, Long idRestaurant) {
        return dishRepository.existsByIdAndRestaurantEntityId(idDish, idRestaurant);
    }

}
