package com.pragma.powerup.usermicroservice.domain.models;

public class OrderDishModel {

    private OrderModel orderModel;
    private DishModel dishModel;
    private int amount;

    public OrderDishModel() {}

    public OrderDishModel(OrderModel orderModel, DishModel dishModel, int amount) {
        this.orderModel = orderModel;
        this.dishModel = dishModel;
        this.amount = amount;
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
