package com.pragma.powerup.usermicroservice.adapters.driving.http.controller;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.DishUpdateDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.DishResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.DishHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.pragma.powerup.usermicroservice.configuration.Constants.DISH_CREATED_MESSAGE;
import static com.pragma.powerup.usermicroservice.configuration.Constants.DISH_UPDATED_MESSAGE;
import static com.pragma.powerup.usermicroservice.configuration.Constants.RESPONSE_MESSAGE_KEY;

@RestController
@RequestMapping("/dish")
@RequiredArgsConstructor
@SecurityRequirement(name = "jwt")
public class DishRestController {

    private final DishHandler dishHandler;

    @Operation(summary = "Add a dish",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Dish created",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Dish already exists",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Error")))})
    @PostMapping("/")
    public ResponseEntity<Map<String, String>> createDish(@RequestBody DishRequestDto dishRequest) {
        dishHandler.createDish(dishRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(RESPONSE_MESSAGE_KEY, DISH_CREATED_MESSAGE));
    }

    @Operation(summary = "Get all the dishes",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All dishes returned",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = DishResponseDto.class)))),
                    @ApiResponse(responseCode = "404", description = "No data found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Error")))})
    @GetMapping("/all")
    public ResponseEntity<List<DishResponseDto>> getAllDishes(
            @Parameter(description = "Number of the page to list dishes") @RequestParam int page) {
        return ResponseEntity.ok(dishHandler.getAllDishes(page));
    }

    @Operation(summary = "Get a dish by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Dish returned",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = DishResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Dish not found with that id",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Error")))})
    @GetMapping("/{id}")
    public ResponseEntity<DishResponseDto> getProvider(@PathVariable Long id) {
        return ResponseEntity.ok(dishHandler.getDishById(id));
    }

    @Operation(summary = "Update a new dish",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Dish updated",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Dish already exists",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Error")))})
    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateDish(@PathVariable Long id,
                                                          @RequestBody DishUpdateDto dishUpdate) {
        dishHandler.updateDish(id, dishUpdate);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(RESPONSE_MESSAGE_KEY, DISH_UPDATED_MESSAGE));
    }


}
