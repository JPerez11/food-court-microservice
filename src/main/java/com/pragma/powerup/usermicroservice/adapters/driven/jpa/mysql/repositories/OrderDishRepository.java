package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.OrderDishEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderDishRepository extends JpaRepository<OrderDishEntity, OrderDishEntity.OrderDishId> {

    @Query("SELECT o FROM OrderDishEntity o " +
            "WHERE o.orderEntity.idEmployee = :employeeId " +
            "AND o.orderEntity.restaurantEntity.id = :restaurantId " +
            "AND upper(o.orderEntity.status) LIKE upper(concat('%', :status, '%'))")
    Page<OrderDishEntity> findOrderDishWithQuery(
            @Param("employeeId") Long employeeId,
            @Param("restaurantId") Long restaurantId,
            @Param("status") String status,
            Pageable pageable);

}
