package com.pragma.powerup.usermicroservice.adapters.driving.http.controller;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.OrderDishRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.OrderDishHandler;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.OrderHandler;
import com.pragma.powerup.usermicroservice.configuration.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
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

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@SecurityRequirement(name = "jwt")
public class OrderRestController {

    private final OrderHandler orderHandler;
    private final OrderDishHandler orderDishHandler;


    @Secured({Constants.CUSTOMER_ROLE_NAME})
    @Operation(summary = "Add a order",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Order created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "404", description = "Something went wrong",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "409", description = "Order already exists",
                        content = @Content(mediaType = "application/json",
                                schema = @Schema(ref = "#/components/schemas/Error")))})
    @PostMapping("/header")
    public ResponseEntity<Map<String, String>> createOrder(@RequestParam Long restaurantId) {
        orderHandler.createOrder(restaurantId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(
                        Constants.RESPONSE_MESSAGE_KEY,
                        Constants.ORDER_CREATED_MESSAGE
                ));
    }

    @Secured({Constants.CUSTOMER_ROLE_NAME})
    @Operation(summary = "Add dish to order",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Detail created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "404", description = "Something went wrong",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "409", description = "Detail already exists",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Error")))})
    @PostMapping("/detail")
    public ResponseEntity<Map<String, String>> createOrderDish(@Valid @RequestBody OrderDishRequestDto orderDishRequestDto) {
        orderDishHandler.createOrderDish(orderDishRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(
                        Constants.RESPONSE_MESSAGE_KEY,
                        Constants.ORDER_DETAIL_CREATED_MESSAGE
                ));
    }

    @Secured({Constants.EMPLOYEE_ROLE_NAME})
    @Operation(summary = "Obtain orders",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Order obtained successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "404", description = "Order not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "409", description = "Parameter conflict",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Error")))})
    @GetMapping("/")
    public ResponseEntity<List<OrderResponseDto>> listOrderDish(
            @Parameter(description = "Page number to limit the list") @RequestParam int page,
            @Parameter(description = "Size of elements per page") @RequestParam int size,
            @Parameter(description = "Status name to filter orders") @RequestParam String status) {
        return ResponseEntity.ok(orderDishHandler.getOrdersByStatus(page, size, status));
    }

    @Secured({Constants.EMPLOYEE_ROLE_NAME})
    @Operation(summary = "Assign employee to order",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Employee assignment successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "404", description = "Order not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "409", description = "Parameter conflict",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Error")))})
    @PatchMapping("/assign")
    public ResponseEntity<Map<String, String>> assignEmployee(
            @Parameter(description = "Id of the order to be assigned to") @RequestParam Long id) {
        orderHandler.assignEmployee(id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(
                        Constants.RESPONSE_MESSAGE_KEY,
                        Constants.ASSIGN_EMPLOYEE_MESSAGE));
    }

    @Secured({Constants.EMPLOYEE_ROLE_NAME})
    @Operation(summary = "Update order status",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Status updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "404", description = "Order not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "409", description = "Parameter conflict",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Error")))})
    @PatchMapping("/status/{id}")
    public ResponseEntity<Map<String, String>> updateOrderStatus(
            @PathVariable Long id,
            @Parameter(description = "Id of the order to be assigned to") @RequestParam String status) {
        orderHandler.updateOrderStatus(id, status);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(
                        Constants.RESPONSE_MESSAGE_KEY,
                        Constants.STATUS_UPDATED_MESSAGE));
    }
    @Secured({Constants.CUSTOMER_ROLE_NAME})
    @Operation(summary = "Cancel order",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Order canceled successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "404", description = "Order not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "409", description = "Conflict",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Error")))})
    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<Map<String, String>> cancelOrder(@PathVariable Long id) {
        orderHandler.cancelOrder(id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(
                        Constants.RESPONSE_MESSAGE_KEY,
                        Constants.ORDER_CANCELED_MESSAGE));
    }

}
