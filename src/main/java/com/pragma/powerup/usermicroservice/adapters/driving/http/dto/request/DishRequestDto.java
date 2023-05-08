package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DishRequestDto {

    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @Min(1)
    private int price;
    @NotBlank
    private String imageUrl;
    @NotNull
    private boolean active = true;
    @Min(0)
    private Long idCategory;
    @Min(0)
    private Long idRestaurant;

}
