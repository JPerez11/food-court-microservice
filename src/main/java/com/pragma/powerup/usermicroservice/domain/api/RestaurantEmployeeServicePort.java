package com.pragma.powerup.usermicroservice.domain.api;

import com.pragma.powerup.usermicroservice.domain.models.RestaurantEmployeeModel;

public interface RestaurantEmployeeServicePort {

    void assignEmployee(RestaurantEmployeeModel restaurantEmployeeModel);

}
