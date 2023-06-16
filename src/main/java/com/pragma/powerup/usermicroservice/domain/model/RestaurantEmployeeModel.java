package com.pragma.powerup.usermicroservice.domain.model;

import java.time.LocalDate;

public class RestaurantEmployeeModel {

    private Long employeeId;
    private RestaurantModel restaurantModel;
    private double salary;
    private LocalDate contract;

    public RestaurantEmployeeModel() {}

    public RestaurantEmployeeModel(Long employeeId, RestaurantModel restaurantModel,
                                   double salary, LocalDate contract) {
        this.employeeId = employeeId;
        this.restaurantModel = restaurantModel;
        this.salary = salary;
        this.contract = contract;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public RestaurantModel getRestaurantModel() {
        return restaurantModel;
    }

    public void setRestaurantModel(RestaurantModel restaurantModel) {
        this.restaurantModel = restaurantModel;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public LocalDate getContract() {
        return contract;
    }

    public void setContract(LocalDate contract) {
        this.contract = contract;
    }
}
