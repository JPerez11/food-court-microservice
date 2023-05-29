package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.DishStatusUpdateDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.DishUpdateDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.DishResponseDto;

import java.util.List;

public interface DishHandler {

    void createDish(DishRequestDto dishRequest);
    DishResponseDto getDishById(Long id);
    List<DishResponseDto> getAllDishes(int page);
    DishResponseDto updateDish(Long id, DishUpdateDto dishUpdateDto);
    DishResponseDto updateDishStatus(Long id, DishStatusUpdateDto dishUpdateStatus);

}
