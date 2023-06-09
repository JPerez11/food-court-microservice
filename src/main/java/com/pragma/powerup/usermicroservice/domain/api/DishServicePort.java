package com.pragma.powerup.usermicroservice.domain.api;

import com.pragma.powerup.usermicroservice.domain.models.DishModel;

import java.util.List;

public interface DishServicePort {

    void createDish(DishModel dishModel);

    DishModel getDishById(Long id);

    List<DishModel> getPaginatedDishesByCategory(Long id, int page, int size, String category);

    DishModel updateDish(Long id, DishModel dishModel);

    DishModel updateDishStatus(Long id, DishModel dishModel);

}
