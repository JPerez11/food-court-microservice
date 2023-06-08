package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.OrderEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.OrderRepository;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.utils.ExtractAuthorization;
import com.pragma.powerup.usermicroservice.configuration.utils.Constants;
import com.pragma.powerup.usermicroservice.domain.model.OrderModel;
import com.pragma.powerup.usermicroservice.domain.spi.OrderPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@RequiredArgsConstructor
public class OrderMysqlAdapter implements OrderPersistencePort {

    private final OrderRepository orderRepository;
    private final OrderEntityMapper orderEntityMapper;

    @Override
    public void createOrder(OrderModel orderModel) {
        orderRepository.save(orderEntityMapper.toOrderEntity(orderModel));
    }

    @Override
    public boolean existsOrderByCustomer(Long id) {
        return orderRepository.existsByIdCustomerAndStatusContainingIgnoreCase(
                id,
                Constants.PENDING_STATUS);
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
    public void assignEmployee(OrderModel orderModel) {
        orderRepository.save(orderEntityMapper.toOrderEntity(orderModel));
    }

    @Override
    public OrderModel getOrderById(Long id) {
        return orderEntityMapper.toOrderModel(
                orderRepository.findById(id).orElse(null)
        );
    }
}
