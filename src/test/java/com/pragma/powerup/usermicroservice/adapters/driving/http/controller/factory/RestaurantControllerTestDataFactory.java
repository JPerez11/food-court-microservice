package com.pragma.powerup.usermicroservice.adapters.driving.http.controller.factory;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.RestaurantRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.RestaurantResponseDto;
import com.pragma.powerup.usermicroservice.domain.model.RestaurantModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

public class RestaurantControllerTestDataFactory {

    private RestaurantControllerTestDataFactory() {}

    public static RestaurantResponseDto getRestaurantResponseFromSetters() {
        RestaurantResponseDto restaurantResponse = new RestaurantResponseDto();
        restaurantResponse.setName("restaurant1");
        restaurantResponse.setTaxIdNumber("1234567890");
        restaurantResponse.setAddress("test address");
        restaurantResponse.setPhoneNumber("1234567890");
        restaurantResponse.setLogoUrl("https://logo-url-test.com");
        restaurantResponse.setIdOwner(2L);

        return restaurantResponse;
    }

    public static RestaurantRequestDto getRestaurantRequestFromSetters() {
        RestaurantRequestDto restaurantRequest = new RestaurantRequestDto();
        restaurantRequest.setName("restaurant1");
        restaurantRequest.setTaxIdNumber("1234567890");
        restaurantRequest.setAddress("test address");
        restaurantRequest.setPhoneNumber("+1234567890");
        restaurantRequest.setLogoUrl("https://logo-url-test.com");
        restaurantRequest.setIdOwner(2L);

        return restaurantRequest;
    }

    public static RestaurantModel getRestaurantFromSetters() {
        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setId(1L);
        restaurantModel.setName("restaurant1");
        restaurantModel.setTaxIdNumber("1234567890");
        restaurantModel.setAddress("test address");
        restaurantModel.setPhoneNumber("1234567890");
        restaurantModel.setLogoUrl("https://logo-url-test.com");
        restaurantModel.setIdOwner(2L);

        return restaurantModel;
    }

    public static String getToken(String role, String email) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("roles", role);
        //Token expiration date
        Date expirationDate = new Date(System.currentTimeMillis() + 3_600_000);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(Keys.hmacShaKeyFor("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXvCJ9".getBytes()))
                .compact();
    }

}
