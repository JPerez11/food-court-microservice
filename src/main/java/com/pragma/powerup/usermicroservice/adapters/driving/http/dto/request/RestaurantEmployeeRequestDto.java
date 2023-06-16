package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class RestaurantEmployeeRequestDto {

    @Min(value = 1, message = "The id must be a number greater than 0")
    @NotNull(message = "Employee id must not be empty")
    private Long employeeId;
    @Min(value = 1, message = "The id must be a number greater than 0")
    @NotNull(message = "Restaurant id must not be empty")
    private Long restaurantId;
    @Min(value = 1, message = "The salary must be a number greater than 0")
    @NotNull(message = "Salary must not be empty")
    private double salary;
    private LocalDate contract;

}
