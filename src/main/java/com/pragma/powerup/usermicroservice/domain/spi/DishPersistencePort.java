package com.pragma.powerup.usermicroservice.domain.spi;

import com.pragma.powerup.usermicroservice.domain.model.DishModel;

import java.util.List;

public interface DishPersistencePort {

    void createDish(DishModel dishModel);

    DishModel getDishById(Long id);

    List<DishModel> getAllDishes(int page);

    DishModel updateDish(Long id, DishModel dishModel);
}
