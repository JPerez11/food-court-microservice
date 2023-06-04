package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderDishRequestDto {

    private Long idOrder;
    private Long idDish;
    private int amount;

}
