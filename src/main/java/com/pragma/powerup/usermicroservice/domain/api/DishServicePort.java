package com.pragma.powerup.usermicroservice.domain.api;

import com.pragma.powerup.usermicroservice.domain.model.DishModel;

import java.util.List;

public interface DishServicePort {

    void createDish(DishModel dishModel);

    DishModel getDishById(Long id);

    List<DishModel> getAllDishes(int page);
    DishModel updateDish(Long id, DishModel dishModel);

}
