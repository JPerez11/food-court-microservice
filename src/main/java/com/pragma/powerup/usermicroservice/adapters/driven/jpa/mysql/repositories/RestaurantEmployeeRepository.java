package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.RestaurantEmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantEmployeeRepository extends JpaRepository<RestaurantEmployeeEntity,
        RestaurantEmployeeEntity.RestaurantEmployeeId> {
}
