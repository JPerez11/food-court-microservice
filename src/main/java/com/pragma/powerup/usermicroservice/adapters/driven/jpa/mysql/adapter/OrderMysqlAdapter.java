package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.OrderEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.OrderRepository;
import com.pragma.powerup.usermicroservice.configuration.utils.Constants;
import com.pragma.powerup.usermicroservice.domain.model.OrderModel;
import com.pragma.powerup.usermicroservice.domain.spi.OrderPersistencePort;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public List<OrderModel> listOrder(int page, int size, Long id, String status) {
        return Collections.emptyList();
    }

    @Override
    public boolean existsOrderByCustomer(Long id) {
        return orderRepository.existsByIdCustomerAndStatusContainingIgnoreCase(
                id,
                Constants.PENDING_STATUS);
    }
}
