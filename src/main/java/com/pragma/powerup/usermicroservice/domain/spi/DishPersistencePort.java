package com.pragma.powerup.usermicroservice.domain.spi;

import com.pragma.powerup.usermicroservice.domain.model.DishModel;

import java.util.List;

public interface DishPersistencePort {

    void createDish(DishModel dishModel);

    DishModel getDishById(Long id);

    List<DishModel> getPaginatedDishesByCategory(Long id, int page, int size, String category);

    DishModel updateDish(Long id, DishModel dishModel);

    DishModel updateDishStatus(Long id, DishModel dishModel);
}
