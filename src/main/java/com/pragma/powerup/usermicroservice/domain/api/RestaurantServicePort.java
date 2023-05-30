package com.pragma.powerup.usermicroservice.domain.api;

import com.pragma.powerup.usermicroservice.domain.model.RestaurantModel;

import java.util.List;

public interface RestaurantServicePort {

    void createRestaurant(RestaurantModel restaurantModel);
    RestaurantModel getRestaurantById(Long id);
    List<RestaurantModel> getAllRestaurants(int pageNumber, int pageSize);

}
