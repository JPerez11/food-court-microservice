package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.OrderEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.OrderRepository;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.RestaurantRepository;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.utils.ExtractAuthorization;
import com.pragma.powerup.usermicroservice.domain.models.OrderModel;
import com.pragma.powerup.usermicroservice.domain.spi.OrderPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
@RequiredArgsConstructor
public class OrderMysqlAdapter implements OrderPersistencePort {

    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrderEntityMapper orderEntityMapper;

    @Override
    public void createOrder(OrderModel orderModel) {
        orderRepository.save(orderEntityMapper.toOrderEntity(orderModel));
    }

    @Override
    public List<OrderModel> findOrderByCustomerIdAndRestaurantId(Long customerId, Long restaurantId) {
        return orderEntityMapper.toModelList(
                orderRepository.findAllByIdCustomerAndRestaurantEntityId(customerId,
                restaurantId));
    }

    @Override
    public boolean existsOrderById(Long id) {
        return orderRepository.existsById(id);
    }

    @Override
    public Long getAuthenticatedUserId() {
        return ExtractAuthorization.getAuthenticatedUserId();
    }

    @Override
    public void updateOrder(OrderModel orderModel) {
        orderRepository.save(orderEntityMapper.toOrderEntity(orderModel));
    }

    @Override
    public OrderModel getOrderById(Long id) {
        return orderEntityMapper.toOrderModel(
                orderRepository.findById(id).orElse(null)
        );
    }

    @Override
    public boolean existsRestaurantById(Long restaurantId) {
        return restaurantRepository.existsById(restaurantId);
    }

    @Override
    public void cancelOrder(Long orderId) {
        orderRepository.cancelOrder(orderId);
    }

    @Override
    public List<OrderModel> findOrderByCustomerId(Long customerId) {
        return orderEntityMapper.toModelList(orderRepository.findOrderEntitiesByIdCustomer(customerId));
    }

    @Override
    public List<OrderModel> showOrderTime(Long ownerId) {
        return orderEntityMapper.toModelList(
                orderRepository.findAllByRestaurantEntityIdOwnerAndEndTimeNotNull(ownerId)
        );
    }

    @Override
    public List<Object[]> orderRanking(Long restaurantId) {
        return orderRepository.getAverageDurationPerEmployee(restaurantId);

    }
}
