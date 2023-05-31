package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class OrderRequestDto {

    private Long idCustomer;
    private Long idEmployee;
    private LocalDateTime date;
    private String status = "PENDING";
    private Long idRestaurant;

}
