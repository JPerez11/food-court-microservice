package com.pragma.powerup.usermicroservice.adapters.driving.http.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.RestaurantAlreadyExistsException;
import com.pragma.powerup.usermicroservice.adapters.driving.http.controller.factory.RestaurantControllerTestDataFactory;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.RestaurantRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.RestaurantResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.RestaurantHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.pragma.powerup.usermicroservice.configuration.utils.Constants.RESTAURANT_CREATED_MESSAGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class RestaurantRestControllerTest {

    @InjectMocks
    RestaurantRestController restaurantRestController;

    @Mock
    RestaurantHandler restaurantHandler;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(restaurantRestController).build();
    }

    @Test
    void shouldCreateRestaurant() throws Exception {
        //Given
        RestaurantRequestDto expectedRestaurantRequest =
                RestaurantControllerTestDataFactory.getRestaurantRequestFromSetters();

        String token =
                RestaurantControllerTestDataFactory.getToken("ADMIN", "test@example.com");

        //When
        mockMvc.perform(post("/restaurant/")
                        .header("Authorization", "Bearer " + token)
                        .content(asJsonString(expectedRestaurantRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(RESTAURANT_CREATED_MESSAGE));

        //Then
        assertDoesNotThrow(RestaurantAlreadyExistsException::new);

    }

    @Test
    void shouldGetRestaurantById() throws Exception {
        //Given
        RestaurantResponseDto expectedRestaurantResponse =
                RestaurantControllerTestDataFactory.getRestaurantResponseFromSetters();

        String token =
                RestaurantControllerTestDataFactory.getToken("ADMIN", "test@example.com");
        //When

        mockMvc.perform(get("/restaurant/1")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        //Then
    }
    @Test
    void shouldGetAllRestaurants() throws Exception {
        //Given
        RestaurantResponseDto expectedRestaurantResponse =
                RestaurantControllerTestDataFactory.getRestaurantResponseFromSetters();

        String token =
                RestaurantControllerTestDataFactory.getToken("ADMIN", "test@example.com");
        int page = 0;
        int size = 10;
        //When

        mockMvc.perform(get("/restaurant/all")
                .param("pageNumber", String.valueOf(page))
                .param("pageSize", String.valueOf(size))
                .header("Authorization", "Bearer " + token)
                .content(asJsonString(expectedRestaurantResponse))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        //Then
    }

    private String asJsonString(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        return objectMapper.writeValueAsString(object);
    }

}