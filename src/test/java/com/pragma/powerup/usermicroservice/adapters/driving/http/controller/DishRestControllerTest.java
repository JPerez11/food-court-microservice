package com.pragma.powerup.usermicroservice.adapters.driving.http.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pragma.powerup.usermicroservice.domain.exceptions.DishAlreadyExistsException;
import com.pragma.powerup.usermicroservice.adapters.driving.http.controller.factory.DishControllerTestDataFactory;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.DishStatusUpdateDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.DishUpdateDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.DishResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.DishHandler;
import com.pragma.powerup.usermicroservice.configuration.utils.Constants;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class DishRestControllerTest {

    @InjectMocks
    DishRestController dishRestController;

    @Mock
    DishHandler dishHandler;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(dishRestController).build();
    }

    @Test
    void shouldCreateDish() throws Exception {
        //Given
        DishRequestDto expected = DishControllerTestDataFactory.getDishRequestFromSetters();
        String token = DishControllerTestDataFactory.getToken("OWNER", "test@example.com");

        //When
        mockMvc.perform(post("/dish/")
                .header("Authorization", "Bearer " + token)
                .content(asJsonString(expected))
                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(Constants.DISH_CREATED_MESSAGE));

        //Then
        assertDoesNotThrow(DishAlreadyExistsException::new);

    }

    @Test
    void shouldGetDishById() throws Exception {
        //Given
        DishResponseDto expected = DishControllerTestDataFactory.getDishResponseFromConstructor();
        String token = DishControllerTestDataFactory.getToken("OWNER", "test@example.com");

        //When
        mockMvc.perform(get("/dish/1")
                        .header("Authorization", "Bearer " + token)
                        .content(asJsonString(expected))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());

    }

    @Test
    void getPaginatedDishesByCategory() throws Exception {
        //Given
        List<DishResponseDto> expected = DishControllerTestDataFactory.getDishesList();
        String token = DishControllerTestDataFactory.getToken("OWNER", "test@example.com");

        //When
        mockMvc.perform(get("/dish/all/1")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .param("category", "category")
                        .header("Authorization", "Bearer " + token)
                        .content(asJsonString(expected))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());

    }

    @Test
    void shouldUpdateDish() throws Exception {
        //Given
        DishUpdateDto expected = DishControllerTestDataFactory.getDishUpdateFromSetters();
        String token = DishControllerTestDataFactory.getToken("OWNER", "test@example.com");

        //When
        mockMvc.perform(patch("/dish/update/1")
                        .header("Authorization", "Bearer " + token)
                        .content(asJsonString(expected))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(Constants.DISH_UPDATED_MESSAGE));

    }

    @Test
    void shouldUpdateDishStatus() throws Exception {
        //Given
        DishStatusUpdateDto expected = DishControllerTestDataFactory.getDishStatusUpdateFromSetters();
        String token = DishControllerTestDataFactory.getToken("OWNER", "test@example.com");

        //When
        mockMvc.perform(patch("/dish/update/status/1")
                        .header("Authorization", "Bearer " + token)
                        .content(asJsonString(expected))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(Constants.DISH_UPDATED_MESSAGE));

    }

    private String asJsonString(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        return objectMapper.writeValueAsString(object);
    }
}