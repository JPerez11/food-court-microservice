package com.pragma.powerup.usermicroservice.adapters.driving.http.controller.factory;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.DishResponseDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DishControllerTestDataFactory {

    private DishControllerTestDataFactory() {}

    public static DishRequestDto getDishRequestFromSetters() {
        DishRequestDto request = new DishRequestDto();
        request.setName("dish1");
        request.setDescription("dishDescription");
        request.setPrice(10000);
        request.setImageUrl("https://image_url_test.com");
        request.setActive(true);
        request.setIdCategory(1L);
        request.setIdRestaurant(1L);

        return request;
    }
    public static DishResponseDto getDishResponseFromSetters() {
        DishResponseDto response = new DishResponseDto();
        response.setName("dish1");
        response.setDescription("dishDescription");
        response.setPrice(10000);
        response.setImageUrl("https://image_url_test.com");
        response.setActive(true);

        return response;
    }

    public static DishResponseDto getDishResponseFromConstructor() {
        return new DishResponseDto(
                "dish2",
                "dish2Description",
                10_000,
                "https://image_url2_test.com",
                true,
                "category"
        );
    }

    public static List<DishResponseDto> getDishesList() {
        List<DishResponseDto> list = new ArrayList<>();
        list.add(getDishResponseFromSetters());
        list.add(getDishResponseFromConstructor());

        return list;
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
