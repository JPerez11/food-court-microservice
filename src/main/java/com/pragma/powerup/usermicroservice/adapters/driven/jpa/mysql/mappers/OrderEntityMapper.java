package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.powerup.usermicroservice.domain.models.OrderModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface OrderEntityMapper {

    @Mapping(target = "restaurantEntity", source = "restaurantModel")
    OrderEntity toOrderEntity(OrderModel orderModel);
    @Mapping(target = "restaurantModel", source = "restaurantEntity")
    OrderModel toOrderModel(OrderEntity orderEntity);
    List<OrderModel> toModelList(List<OrderEntity> orderEntityList);

}
