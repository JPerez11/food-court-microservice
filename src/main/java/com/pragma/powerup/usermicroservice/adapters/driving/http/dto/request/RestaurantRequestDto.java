package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request;

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

    @NotBlank @Pattern(regexp = "^(?=.*[a-zA-Z])[a-zA-Z\\d]*$",
            message = "The name of the restaurant cannot be just numbers")
    private String name;
    @NotBlank @Pattern(regexp = "^\\d{8,12}$")
    private String taxIdNumber;
    @NotBlank
    private String address;
    @NotBlank @Pattern(regexp = "^\\+?\\d{10,12}$")
    private String phoneNumber;
    @NotBlank
    private String logoUrl;
    @NotBlank
    private Long idOwner;

}
