package com.pragma.powerup.usermicroservice.adapters.driving.http.controller;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.RestaurantEmployeeRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.RestaurantRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.RestaurantResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.RestaurantEmployeeHandler;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.RestaurantHandler;
import com.pragma.powerup.usermicroservice.configuration.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.pragma.powerup.usermicroservice.configuration.utils.Constants.RESPONSE_MESSAGE_KEY;
import static com.pragma.powerup.usermicroservice.configuration.utils.Constants.RESTAURANT_CREATED_MESSAGE;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
@SecurityRequirement(name = "jwt")
public class RestaurantRestController {

    private final RestaurantHandler restaurantHandler;
    private final RestaurantEmployeeHandler restaurantEmployeeHandler;

    @Secured({Constants.ADMIN_ROLE_NAME})
    @Operation(summary = "Add a new restaurant",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Restaurant created",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Restaurant already exists",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Error")))})
    @PostMapping("/")
    public ResponseEntity<Map<String, String>> createRestaurant(@Valid
                                                                    @RequestBody RestaurantRequestDto restaurantRequest) {
        restaurantHandler.createRestaurant(restaurantRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(RESPONSE_MESSAGE_KEY, RESTAURANT_CREATED_MESSAGE));
    }

    @Secured({Constants.ADMIN_ROLE_NAME, Constants.OWNER_ROLE_NAME})
    @Operation(summary = "Get all the restaurants",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All restaurants returned",
                            content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RestaurantResponseDto.class)))),
                    @ApiResponse(responseCode = "404", description = "No data found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Error")))})
    @GetMapping("/all")
    public ResponseEntity<List<RestaurantResponseDto>> getAllRestaurants(
            @RequestParam int pageNumber, @RequestParam int pageSize) {
        return ResponseEntity.ok(restaurantHandler.getAllRestaurants(pageNumber, pageSize));
    }

    @Secured({Constants.ADMIN_ROLE_NAME, Constants.OWNER_ROLE_NAME})
    @Operation(summary = "Get a restaurant by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Restaurant returned",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = RestaurantResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Restaurant not found with that id",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Error")))})
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponseDto> getRestaurantById(@PathVariable Long id) {
        return ResponseEntity.ok(restaurantHandler.getRestaurantById(id));
    }

    @Secured({Constants.OWNER_ROLE_NAME})
    @Operation(summary = "Assign employee to restaurant",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Employee assign successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "403", description = "Unauthorized access",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "404", description = "No data found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "409", description = "Employee has already been assigned or user has " +
                            "not employee role",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Error")))})
    @PostMapping("/assign-employee")
    public ResponseEntity<Map<String, String>> assignEmployee(@Valid
                                                                  @RequestBody RestaurantEmployeeRequestDto requestDto) {
        restaurantEmployeeHandler.assignEmployee(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(
                        Constants.RESPONSE_MESSAGE_KEY,
                        Constants.ASSIGN_EMPLOYEE_MESSAGE
                ));
    }
}
