package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.domain.api.RestaurantEmployeeServicePort;
import com.pragma.powerup.usermicroservice.domain.model.RestaurantEmployeeModel;
import com.pragma.powerup.usermicroservice.domain.spi.RestaurantEmployeePersistencePort;

public class RestaurantEmployeeUseCase implements RestaurantEmployeeServicePort {

    private final RestaurantEmployeePersistencePort persistencePort;

    public RestaurantEmployeeUseCase(RestaurantEmployeePersistencePort persistencePort) {
        this.persistencePort = persistencePort;
    }

    @Override
    public void assignEmployee(RestaurantEmployeeModel restaurantEmployeeModel) {
        persistencePort.assignEmployee(restaurantEmployeeModel);
    }

}
