package com.pragma.powerup.usermicroservice.domain.models;

import java.time.LocalDateTime;

public class OrderModel {

    private Long id;
    private Long idCustomer;
    private Long idEmployee;
    private LocalDateTime date;
    private String status;
    private RestaurantModel restaurantModel;

    public OrderModel() {}

    public OrderModel(Long id, Long idCustomer, Long idEmployee, LocalDateTime date, String status,
                      RestaurantModel restaurantModel) {
        this.id = id;
        this.idCustomer = idCustomer;
        this.idEmployee = idEmployee;
        this.date = date;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
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
