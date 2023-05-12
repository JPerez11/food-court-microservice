package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantRequestDto {

    @NotBlank(message = "The name must not be empty")
    @Pattern(regexp = "^(?=.*[a-zA-Z])[a-zA-Z\\d]*$",
            message = "The name of the restaurant cannot be just numbers")
    private String name;
    @NotBlank(message = "The NIT must not be empty")
    @Pattern(regexp = "^\\d{9,13}$",
            message = "The NIT can only contain numbers and must not extend the 13 characters")
    private String taxIdNumber;
    @NotBlank(message = "Address must not be empty")
    private String address;
    @NotBlank(message = "The Phone number must not be empty")
    @Pattern(regexp = "^\\+?\\d{9,12}$",
            message = "The phone number cannot be longer than 13 characters and can only contain a '+'")
    private String phoneNumber;
    @NotBlank(message = "The logo URL must not be empty")
    private String logoUrl;
    @Min(value = 1, message = "The id must be a number greater than 0")
    @NotBlank(message = "Owner id must not be empty")
    private Long idOwner;

}
