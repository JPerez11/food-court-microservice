package com.pragma.powerup.usermicroservice.domain.models;

import java.time.LocalDateTime;

public class OrderModel {

    private Long id;
    private Long idCustomer;
    private Long idEmployee;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    private RestaurantModel restaurantModel;

    public OrderModel() {}

    public OrderModel(Long id, Long idCustomer, Long idEmployee, LocalDateTime startTime, LocalDateTime endTime,
                      String status,
                      RestaurantModel restaurantModel) {
        this.id = id;
        this.idCustomer = idCustomer;
        this.idEmployee = idEmployee;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.restaurantModel = restaurantModel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Long idCustomer) {
        this.idCustomer = idCustomer;
    }

    public Long getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Long idEmployee) {
        this.idEmployee = idEmployee;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public RestaurantModel getRestaurantModel() {
        return restaurantModel;
    }

    public void setRestaurantModel(RestaurantModel restaurantModel) {
        this.restaurantModel = restaurantModel;
    }
}
