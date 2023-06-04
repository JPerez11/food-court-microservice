package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.OrderDishEntity;
import com.pragma.powerup.usermicroservice.domain.model.OrderDishModel;
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
    @Mapping(target = "orderDishId", expression = "java(new OrderDishEntity.OrderDishId(" +
            "orderDishModel.getOrderModel().getId(), orderDishModel.getDishModel().getId()))")
    OrderDishEntity toOrderDishEntity(OrderDishModel orderDishModel);
    List<OrderDishEntity> toEntityList(List<OrderDishModel> modelList);

}
