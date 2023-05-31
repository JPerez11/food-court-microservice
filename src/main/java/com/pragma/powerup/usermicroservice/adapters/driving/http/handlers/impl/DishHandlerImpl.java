package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.DishUpdateDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.DishStatusUpdateDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.DishResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.DishHandler;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.DishRequestMapper;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.DishResponseMapper;
import com.pragma.powerup.usermicroservice.domain.api.DishServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DishHandlerImpl implements DishHandler {

    private final DishServicePort dishServicePort;
    private final DishRequestMapper dishRequestMapper;
    private final DishResponseMapper dishResponseMapper;

    @Override
    public void createDish(DishRequestDto dishRequest) {
        dishServicePort.createDish(
                dishRequestMapper.toDishModel(dishRequest)
        );
    }

    @Override
    public DishResponseDto getDishById(Long id) {
        return dishResponseMapper.toDishResponse(
                dishServicePort.getDishById(id)
        );
    }

    @Override
    public List<DishResponseDto> getPaginatedDishesByCategory(Long id, int page, int size, String category) {
        return dishResponseMapper.toDishResponseList(
                dishServicePort.getPaginatedDishesByCategory(id, page, size, category)
        );
    }

    @Override
    public DishResponseDto updateDish(Long id, DishUpdateDto dishUpdateDto) {
        return dishResponseMapper.toDishResponse(
                dishServicePort.updateDish(id, dishRequestMapper.toDishModelUpdate(
                        dishUpdateDto
                ))
        );
    }

    @Override
    public DishResponseDto updateDishStatus(Long id, DishStatusUpdateDto dishUpdateStatus) {
        return dishResponseMapper.toDishResponse(
                dishServicePort.updateDishStatus(id, dishRequestMapper.toDishModelStatusUpdate(
                        dishUpdateStatus
                ))
        );
    }
}
