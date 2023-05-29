package com.pragma.powerup.usermicroservice.adapters.driving.http.mapper;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.DishUpdateDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.DishStatusUpdateDto;
import com.pragma.powerup.usermicroservice.domain.model.DishModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface DishRequestMapper {

    @Mapping(target = "categoryModel.id", source = "idCategory")
    @Mapping(target = "restaurantModel.id", source = "idRestaurant")
    DishModel toDishModel(DishRequestDto dishRequest);

    DishModel toDishModelUpdate(DishUpdateDto dishUpdate);

    DishModel toDishModelStatusUpdate(DishStatusUpdateDto dishUpdateStatus);

}
