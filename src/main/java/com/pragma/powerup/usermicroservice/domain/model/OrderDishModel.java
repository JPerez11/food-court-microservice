package com.pragma.powerup.usermicroservice.domain.model;

public class OrderDishModel {

    private Long id;
    private OrderModel orderModel;
    private DishModel dishModel;
    private int amount;

    public OrderDishModel() {}

    public OrderDishModel(Long id, OrderModel orderModel, DishModel dishModel, int amount) {
        this.id = id;
        this.orderModel = orderModel;
        this.dishModel = dishModel;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderModel getOrderModel() {
        return orderModel;
    }

    public void setOrderModel(OrderModel orderModel) {
        this.orderModel = orderModel;
    }

    public DishModel getDishModel() {
        return dishModel;
    }

    public void setDishModel(DishModel dishModel) {
        this.dishModel = dishModel;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
