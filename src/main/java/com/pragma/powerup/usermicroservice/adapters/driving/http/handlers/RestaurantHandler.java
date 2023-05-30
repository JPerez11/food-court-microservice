package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.RestaurantRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.RestaurantResponseDto;

import java.util.List;

public interface RestaurantHandler {

    void createRestaurant(RestaurantRequestDto restaurantRequest);
    RestaurantResponseDto getRestaurantById(Long id);
    List<RestaurantResponseDto> getAllRestaurants(int pageNumber, int pageSize);

}
