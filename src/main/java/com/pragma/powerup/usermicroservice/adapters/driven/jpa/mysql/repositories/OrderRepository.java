package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    boolean existsByIdAndIdCustomer(Long idOrder, Long idCustomer);

    List<OrderEntity> findAllByIdCustomerAndRestaurantEntityId(Long customerId, Long restaurantId);
    boolean existsByIdAndIdCustomerAndStatusContainingIgnoreCase(Long idOrder, Long idCustomer, String status);
    boolean existsByIdEmployee(Long idEmployee);

    @Modifying
    @Query("UPDATE OrderEntity o " +
            "SET o.status = 'CANCELED' " +
            "WHERE o.id = :orderId")
    void cancelOrder(@Param("orderId") Long orderId);
    List<OrderEntity> findOrderEntitiesByIdCustomer(Long customerId);
    List<OrderEntity> findAllByRestaurantEntityIdOwnerAndEndTimeNotNull(Long restaurantId);

    @Query("SELECT o.idEmployee, AVG(TIMESTAMPDIFF(second, o.startTime, o.endTime)) " +
            "FROM OrderEntity o " +
            "WHERE o.endTime IS NOT NULL " +
            "AND o.restaurantEntity.id = :restaurantId " +
            "GROUP BY o.idEmployee " +
            "ORDER BY AVG(TIMESTAMPDIFF(second, o.startTime, o.endTime))")
    List<Object[]> getAverageDurationPerEmployee(@Param("restaurantId") Long restaurantId);
}
