package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.OrderAlreadyExistsException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.OrderEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.OrderRepository;
import com.pragma.powerup.usermicroservice.configuration.utils.Constants;
import com.pragma.powerup.usermicroservice.domain.model.OrderModel;
import com.pragma.powerup.usermicroservice.domain.spi.OrderPersistencePort;
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
        if (orderModel == null) {
            throw new NullPointerException();
        }
        if (orderRepository.existsByIdCustomerAndStatusContainingIgnoreCase(
                orderModel.getIdCustomer(),
                Constants.PENDING_STATUS)) {
            throw new OrderAlreadyExistsException();
        }
        orderModel.setStatus(Constants.PENDING_STATUS);
        orderRepository.save(orderEntityMapper.toOrderEntity(orderModel));
    }

    @Override
    public List<OrderModel> listOrder(int page, int size, Long id, String status) {
        return null;
    }
}
