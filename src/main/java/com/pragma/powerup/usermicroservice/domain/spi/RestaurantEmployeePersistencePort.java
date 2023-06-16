package com.pragma.powerup.usermicroservice.domain.spi;

import com.pragma.powerup.usermicroservice.domain.model.RestaurantEmployeeModel;

public interface RestaurantEmployeePersistencePort {

    void assignEmployee(RestaurantEmployeeModel restaurantEmployeeModel);

}
