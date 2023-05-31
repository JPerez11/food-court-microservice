package com.pragma.powerup.usermicroservice.adapters.driving.http.controller;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.OrderHandler;
import com.pragma.powerup.usermicroservice.configuration.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@SecurityRequirement(name = "jwt")
public class OrderRestController {

    private final OrderHandler orderHandler;


    @Secured({Constants.CUSTOMER_ROLE})
    @Operation(summary = "Add a order",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Order created",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Map"))),
                        @ApiResponse(responseCode = "409", description = "Order already exists",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Error")))})
    @PostMapping("/")
    public ResponseEntity<Map<String, String>> createOrder(@Valid @RequestBody OrderRequestDto orderRequest) {
        orderHandler.createOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(
                        Constants.RESPONSE_MESSAGE_KEY,
                        Constants.ORDER_CREATED_MESSAGE
                ));
    }
}
