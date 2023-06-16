package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.RestaurantEmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RestaurantEmployeeRepository extends JpaRepository<RestaurantEmployeeEntity,
        RestaurantEmployeeEntity.RestaurantEmployeeId> {

    boolean existsByEmployeeId(Long employeeId);
    @Query("SELECT re.restaurantEntity.id FROM RestaurantEmployeeEntity re " +
            "WHERE re.employeeId = :employeeId")
    Long findRestaurantIdByEmployeeId(@Param("employeeId") Long employeeId);

}
