package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.pragma.powerup.usermicroservice.domain.model.DishModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface DishEntityMapper {

    @Mapping(target = "categoryModel.id", source = "categoryEntity.id")
    @Mapping(target = "restaurantModel.id", source = "restaurantEntity.id")
    DishModel toDishModel(DishEntity dishEntity);
    @Mapping(target = "categoryEntity.id", source = "categoryModel.id")
    @Mapping(target = "restaurantEntity.id", source = "restaurantModel.id")
    DishEntity toDishEntity(DishModel dishModel);
    List<DishModel> toDishModelList(List<DishEntity> dishEntityList);
    List<DishEntity> toDishEntityList(List<DishModel> dishModelList);

}
