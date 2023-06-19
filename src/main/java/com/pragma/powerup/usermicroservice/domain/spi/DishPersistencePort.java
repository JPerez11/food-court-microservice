package com.pragma.powerup.usermicroservice.domain.spi;

import com.pragma.powerup.usermicroservice.domain.models.CategoryModel;
import com.pragma.powerup.usermicroservice.domain.models.DishModel;
import com.pragma.powerup.usermicroservice.domain.models.RestaurantModel;

import java.util.List;

public interface DishPersistencePort {

    void createDish(DishModel dishModel);

    DishModel getDishById(Long id);

    List<DishModel> getPaginatedDishesByCategory(Long id, int page, int size, String category);

    DishModel updateDish(Long id, DishModel dishModel);

    RestaurantModel getRestaurantById(Long id);
    Long getAuthenticatedUserId();
    CategoryModel getCategoryById(Long id);
    boolean existsCategoryByName(String category);
    boolean existsRestaurantById(Long id);
}
