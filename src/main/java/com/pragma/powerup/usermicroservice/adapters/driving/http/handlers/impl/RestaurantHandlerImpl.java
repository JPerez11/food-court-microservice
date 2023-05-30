package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.RestaurantRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.RestaurantResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.RestaurantHandler;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.RestaurantRequestMapper;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.RestaurantResponseMapper;
import com.pragma.powerup.usermicroservice.domain.api.RestaurantServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantHandlerImpl implements RestaurantHandler {

    private final RestaurantServicePort restaurantServicePort;
    private final RestaurantRequestMapper restaurantRequestMapper;
    private final RestaurantResponseMapper restaurantResponseMapper;

    @Override
    public void createRestaurant(RestaurantRequestDto restaurantRequest) {
        restaurantServicePort.createRestaurant(
                restaurantRequestMapper.toRestaurantModel(restaurantRequest)
        );
    }

    @Override
    public RestaurantResponseDto getRestaurantById(Long id) {
        return restaurantResponseMapper.toRestaurantResponse(
                restaurantServicePort.getRestaurantById(id)
        );
    }

    @Override
    public List<RestaurantResponseDto> getAllRestaurants(int pageNumber, int pageSize) {
        return restaurantResponseMapper.toRestaurantResponseList(
                restaurantServicePort.getAllRestaurants(pageNumber, pageSize)
        );
    }
}
