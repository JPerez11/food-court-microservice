package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {

    private LocalDateTime date;
    private String status;
    private Long idCustomer;
    private Long idEmployee;
    private String restaurantName;
    private List<OrderDishResponseDto> detail;

}
