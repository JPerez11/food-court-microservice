package com.pragma.powerup.usermicroservice.domain.spi;

import com.pragma.powerup.usermicroservice.domain.model.RestaurantModel;

import java.util.List;

public interface RestaurantPersistencePort {

    void createRestaurant(RestaurantModel restaurantModel);
    RestaurantModel getRestaurantById(Long id);
    List<RestaurantModel> getAllRestaurants(int pageNumber, int pageSize);
    // Methods to catch truth value
    boolean existsRestaurantByTaxIdNumber(String taxIdNumber);

}
