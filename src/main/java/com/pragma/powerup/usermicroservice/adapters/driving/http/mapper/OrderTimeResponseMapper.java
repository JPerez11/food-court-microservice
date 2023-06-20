package com.pragma.powerup.usermicroservice.adapters.driving.http.mapper;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.OrderTimeResponseDto;
import com.pragma.powerup.usermicroservice.domain.models.OrderModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface OrderTimeResponseMapper {

    @Mapping(target = "customerId", source = "idCustomer")
    @Mapping(target = "startTime", expression = "java(orderModel.getStartTime().format(" +
            "java.time.format.DateTimeFormatter.ofPattern(\"yyyy-MM-dd HH:mm:ss\")))")
    @Mapping(target = "endTime", expression = "java(orderModel.getEndTime().format(" +
            "java.time.format.DateTimeFormatter.ofPattern(\"yyyy-MM-dd HH:mm:ss\")))")
    @Mapping(target = "timePeriod", expression = "java(formatDuration(orderModel.getStartTime(), orderModel.getEndTime()))")
    OrderTimeResponseDto toResponse(OrderModel orderModel);

    List<OrderTimeResponseDto> toResponseList(List<OrderModel> orderModelList);

    default String formatDuration(LocalDateTime startTime, LocalDateTime endTime) {
        Duration duration = Duration.between(startTime, endTime);
        long hours = duration.toHours();
        long minutes = (duration.toMinutes() % 60);
        long seconds = (duration.getSeconds() % 60);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
