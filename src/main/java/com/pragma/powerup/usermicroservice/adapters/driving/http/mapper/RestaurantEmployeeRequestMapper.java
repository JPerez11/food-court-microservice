package com.pragma.powerup.usermicroservice.adapters.driving.http.mapper;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.RestaurantEmployeeRequestDto;
import com.pragma.powerup.usermicroservice.domain.models.RestaurantEmployeeModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RestaurantEmployeeRequestMapper {

    @Mapping(source = "employeeId", target = "employeeId")
    @Mapping(source = "restaurantId", target = "restaurantModel.id")
    RestaurantEmployeeModel toModel(RestaurantEmployeeRequestDto requestDto);

}
