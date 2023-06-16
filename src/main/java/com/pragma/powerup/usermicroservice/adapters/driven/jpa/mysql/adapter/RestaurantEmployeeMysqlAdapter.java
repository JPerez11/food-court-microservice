package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.RestaurantEmployeeEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.RestaurantEmployeeRepository;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.RestaurantRepository;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.utils.ExtractAuthorization;
import com.pragma.powerup.usermicroservice.domain.model.RestaurantEmployeeModel;
import com.pragma.powerup.usermicroservice.domain.spi.RestaurantEmployeePersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
public class RestaurantEmployeeMysqlAdapter implements RestaurantEmployeePersistencePort {

    private final RestaurantEmployeeRepository restaurantEmployeeRepository;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantEmployeeEntityMapper restaurantEmployeeEntityMapper;

    @Override
    public void assignEmployee(RestaurantEmployeeModel restaurantEmployeeModel) {
        restaurantEmployeeRepository.save(
                restaurantEmployeeEntityMapper.toEntity(restaurantEmployeeModel)
        );
    }

    @Override
    public boolean existsAssignedEmployee(Long employeeId) {
        return restaurantEmployeeRepository.existsByEmployeeId(employeeId);
    }

    @Override
    public boolean existsRestaurant(Long restaurantId) {
        return restaurantRepository.existsById(restaurantId);
    }

    @Override
    public String getAuthenticatedRole() {
        return ExtractAuthorization.getAuthenticatedRole();
    }
}
