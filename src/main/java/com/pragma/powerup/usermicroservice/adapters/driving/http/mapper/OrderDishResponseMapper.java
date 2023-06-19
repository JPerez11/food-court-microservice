package com.pragma.powerup.usermicroservice.adapters.driving.http.mapper;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.OrderDishResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.pragma.powerup.usermicroservice.domain.models.OrderDishModel;
import com.pragma.powerup.usermicroservice.domain.models.OrderModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface OrderDishResponseMapper {


    default List<OrderResponseDto> toOrderResponseList(List<OrderDishModel> orderDishModelList) {
        Map<OrderModel, List<OrderDishModel>> orderDetailMap = new LinkedHashMap<>();

        for (OrderDishModel orderDishModel : orderDishModelList) {
            OrderModel orderModel = orderDishModel.getOrderModel();
            List<OrderDishModel> detailList = orderDetailMap.getOrDefault(orderModel, new ArrayList<>());
            detailList.add(orderDishModel);
            orderDetailMap.put(orderModel, detailList);
        }

        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();
        for (Map.Entry<OrderModel, List<OrderDishModel>> entry : orderDetailMap.entrySet()) {
            OrderModel orderModel = entry.getKey();
            List<OrderDishModel> detailList = entry.getValue();

            OrderResponseDto orderResponseDto = new OrderResponseDto();
            orderResponseDto.setDate(orderModel.getDate());
            orderResponseDto.setStatus(orderModel.getStatus());
            orderResponseDto.setIdCustomer(orderModel.getIdCustomer());
            orderResponseDto.setIdEmployee(orderModel.getIdEmployee());
            orderResponseDto.setRestaurantName(orderModel.getRestaurantModel().getName());

            List<OrderDishResponseDto> orderDishResponseDtoList = new ArrayList<>();
            for (OrderDishModel orderDishModel : detailList) {
                OrderDishResponseDto orderDishResponseDto = new OrderDishResponseDto();
                orderDishResponseDto.setName(orderDishModel.getDishModel().getName());
                orderDishResponseDto.setPrice(orderDishModel.getDishModel().getPrice());
                orderDishResponseDto.setImageUrl(orderDishModel.getDishModel().getImageUrl());
                orderDishResponseDto.setCategoryName(orderDishModel.getDishModel().getCategoryModel().getName());
                orderDishResponseDto.setAmount(orderDishModel.getAmount());

                orderDishResponseDtoList.add(orderDishResponseDto);
            }
            orderResponseDto.setDetail(orderDishResponseDtoList);

            orderResponseDtoList.add(orderResponseDto);
        }

        return orderResponseDtoList;
    }

    @Mapping(target = "name", source = "dishModel.name")
    @Mapping(target = "price", source = "dishModel.price")
    @Mapping(target = "imageUrl", source = "dishModel.imageUrl")
    @Mapping(target = "categoryName", source = "dishModel.categoryModel.name")
    @Mapping(target = "amount", source = "amount")
    OrderDishResponseDto toOrderDishResponse(OrderDishModel orderDishModel);


}
