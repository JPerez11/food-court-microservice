package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.domain.api.RestaurantServicePort;
import com.pragma.powerup.usermicroservice.domain.model.RestaurantModel;
import com.pragma.powerup.usermicroservice.domain.spi.RestaurantPersistencePort;
import com.pragma.powerup.usermicroservice.domain.validations.RestaurantValidation;

import java.util.List;

public class RestaurantUseCase implements RestaurantServicePort {

    private final RestaurantPersistencePort restaurantPersistencePort;

    public RestaurantUseCase(RestaurantPersistencePort restaurantPersistencePort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
    }

    @Override
    public void createRestaurant(RestaurantModel restaurantModel) {
        RestaurantValidation.restaurantValidate(restaurantModel);
        restaurantPersistencePort.createRestaurant(restaurantModel);
    }

    @Override
    public RestaurantModel getRestaurantById(Long id) {
        RestaurantModel restaurantModel = restaurantPersistencePort.getRestaurantById(id);
        RestaurantValidation.getRestaurantValidate(restaurantModel);
        return restaurantModel;
    }

    @Override
    public List<RestaurantModel> getAllRestaurants() {
        List<RestaurantModel> restaurantModelList = restaurantPersistencePort.getAllRestaurants();
        RestaurantValidation.getAllRestaurantsValidate(restaurantModelList);
        return restaurantModelList;
    }
}
