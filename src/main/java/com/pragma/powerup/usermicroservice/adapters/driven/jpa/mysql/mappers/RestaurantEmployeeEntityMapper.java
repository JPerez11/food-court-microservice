package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.RestaurantEmployeeEntity;
import com.pragma.powerup.usermicroservice.domain.model.RestaurantEmployeeModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RestaurantEmployeeEntityMapper {

    @Mapping(source = "employeeId", target = "employeeId")
    @Mapping(source = "restaurantEntity", target = "restaurantModel")
    RestaurantEmployeeModel toModel(RestaurantEmployeeEntity entity);
    @Mapping(source = "employeeId", target = "employeeId")
    @Mapping(source = "restaurantModel", target = "restaurantEntity")
    @Mapping(target = "restaurantEmployeeId", expression = "java(new RestaurantEmployeeEntity.RestaurantEmployeeId(" +
            "model.getEmployeeId(), model.getRestaurantModel().getId()))")
    RestaurantEmployeeEntity toEntity(RestaurantEmployeeModel model);

}
