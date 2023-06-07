package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.OrderDishEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDishRepository extends JpaRepository<OrderDishEntity, OrderDishEntity.OrderDishId> {

    Page<OrderDishEntity> findAllByOrderEntityIdEmployeeAndOrderEntityStatusContainingIgnoreCase(
            Long idCustomer, String status, Pageable pageable);

}
