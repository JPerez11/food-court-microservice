package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.RestaurantEmployeeRequestDto;

public interface RestaurantEmployeeHandler {

    void assignEmployee(RestaurantEmployeeRequestDto requestDto);

}
