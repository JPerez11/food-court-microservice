package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.OrderDishEntity;
import com.pragma.powerup.usermicroservice.domain.models.OrderDishModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface OrderDishEntityMapper {

    @Mapping(target = "orderEntity", source = "orderModel")
    @Mapping(target = "dishEntity", source = "dishModel")
    @Mapping(target = "orderEntity.restaurantEntity", source = "orderModel.restaurantModel")
    @Mapping(target = "dishEntity.restaurantEntity", source = "dishModel.restaurantModel")
    @Mapping(target = "dishEntity.categoryEntity", source = "dishModel.categoryModel")
    @Mapping(target = "orderDishId", expression = "java(new OrderDishEntity.OrderDishId(" +
            "orderDishModel.getOrderModel().getId(), orderDishModel.getDishModel().getId()))")
    OrderDishEntity toOrderDishEntity(OrderDishModel orderDishModel);
    @Mapping(target = "orderModel", source = "orderEntity")
    @Mapping(target = "dishModel", source = "dishEntity")
    @Mapping(target = "orderModel.restaurantModel", source = "orderEntity.restaurantEntity")
    @Mapping(target = "dishModel.restaurantModel", source = "dishEntity.restaurantEntity")
    @Mapping(target = "dishModel.categoryModel", source = "dishEntity.categoryEntity")
    OrderDishModel toOrderDishModel(OrderDishEntity orderDishEntity);
    List<OrderDishEntity> toEntityList(List<OrderDishModel> modelList);
    List<OrderDishModel> toModelList(List<OrderDishEntity> entityList);

}
