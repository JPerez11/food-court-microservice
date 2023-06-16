package com.pragma.powerup.usermicroservice.domain.api;

import com.pragma.powerup.usermicroservice.domain.model.RestaurantEmployeeModel;

public interface RestaurantEmployeeServicePort {

    void assignEmployee(RestaurantEmployeeModel restaurantEmployeeModel);

}
