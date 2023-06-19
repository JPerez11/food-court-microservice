package com.pragma.powerup.usermicroservice.domain.spi;

import com.pragma.powerup.usermicroservice.domain.models.RestaurantEmployeeModel;

public interface RestaurantEmployeePersistencePort {

    void assignEmployee(RestaurantEmployeeModel restaurantEmployeeModel);
    boolean existsAssignedEmployee(Long employeeId);
    boolean existsRestaurant(Long restaurantId);
    String getAuthenticatedRole();
}
