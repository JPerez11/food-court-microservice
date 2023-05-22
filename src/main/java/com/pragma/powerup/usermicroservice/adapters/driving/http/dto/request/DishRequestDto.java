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

    @NotBlank(message = "Name must not be empty")
    private String name;
    @NotBlank(message = "Description must not be empty")
    private String description;
    @Min(value = 1, message = "The price must be a positive number greater than 0")
    private int price;
    @NotBlank(message = "The image url must not be empty")
    private String imageUrl;
    @NotNull
    private boolean active = true;
    @Min(value = 1, message = "The id category must be a positive number greater than 0")
    private Long idCategory;
    @Min(value = 1, message = "The id restaurant must be a positive number greater than 0")
    private Long idRestaurant;

}
