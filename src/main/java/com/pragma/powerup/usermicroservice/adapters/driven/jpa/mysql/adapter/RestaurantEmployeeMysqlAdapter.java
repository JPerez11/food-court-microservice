package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.RestaurantEmployeeEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.RestaurantEmployeeRepository;
import com.pragma.powerup.usermicroservice.domain.model.RestaurantEmployeeModel;
import com.pragma.powerup.usermicroservice.domain.spi.RestaurantEmployeePersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
public class RestaurantEmployeeMysqlAdapter implements RestaurantEmployeePersistencePort {

    private final RestaurantEmployeeRepository restaurantEmployeeRepository;
    private final RestaurantEmployeeEntityMapper restaurantEmployeeEntityMapper;

    @Override
    public void assignEmployee(RestaurantEmployeeModel restaurantEmployeeModel) {
        restaurantEmployeeRepository.save(
                restaurantEmployeeEntityMapper.toEntity(restaurantEmployeeModel)
        );
    }
}
