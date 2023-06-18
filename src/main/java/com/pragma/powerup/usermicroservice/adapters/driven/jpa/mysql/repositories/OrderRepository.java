package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    boolean existsByIdAndIdCustomer(Long idOrder, Long idCustomer);
    boolean existsByIdCustomerAndStatusContainingIgnoreCase(Long idCustomer, String status);
    boolean existsByIdAndIdCustomerAndStatusContainingIgnoreCase(Long idOrder, Long idCustomer, String status);
    boolean existsByIdEmployee(Long idEmployee);

    @Modifying
    @Query("UPDATE OrderEntity o " +
            "SET o.status = 'CANCELED' " +
            "WHERE o.id = :orderId")
    void cancelOrder(@Param("orderId") Long orderId);
}
