package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantResponseDto {

    private String name;
    private String taxIdNumber;
    private String address;
    private String phoneNumber;
    private String logoUrl;
    private Long idOwner;

}
