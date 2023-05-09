package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DishUpdateDto {

    @NotBlank(message = "Description must not be empty")
    private String description;
    @Min(value = 1, message = "The price must be a positive number greater than 0")
    private int price;

}