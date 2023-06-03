package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.domain.api.DishServicePort;
import com.pragma.powerup.usermicroservice.domain.exceptions.CategoryNotFoundException;
import com.pragma.powerup.usermicroservice.domain.exceptions.OwnerNotAuthorizedForUpdateException;
import com.pragma.powerup.usermicroservice.domain.exceptions.RestaurantNotFoundException;
import com.pragma.powerup.usermicroservice.domain.model.CategoryModel;
import com.pragma.powerup.usermicroservice.domain.model.DishModel;
import com.pragma.powerup.usermicroservice.domain.model.RestaurantModel;
import com.pragma.powerup.usermicroservice.domain.spi.DishPersistencePort;
import com.pragma.powerup.usermicroservice.domain.validations.DishValidation;

import java.util.List;
import java.util.Objects;

public class DishUseCase implements DishServicePort {

    private final DishPersistencePort dishPersistencePort;

    public DishUseCase(DishPersistencePort dishPersistencePort) {
        this.dishPersistencePort = dishPersistencePort;
    }

    @Override
    public void createDish(DishModel dishModel) {
        if (dishModel == null) {
            throw new NullPointerException();
        }
        RestaurantModel restaurant = dishPersistencePort.getRestaurantById(
                dishModel.getRestaurantModel().getId());
        if (restaurant == null) {
            throw new RestaurantNotFoundException();
        }
        dishModel.setRestaurantModel(restaurant);
        Long authenticatedUserId = dishPersistencePort.getAuthenticatedUserId();
        if (!Objects.equals(restaurant.getIdOwner(), authenticatedUserId)) {
            throw new OwnerNotAuthorizedForUpdateException();
        }
        CategoryModel category = dishPersistencePort.getCategoryById(
                dishModel.getRestaurantModel().getId());
        if (category == null) {
            throw new CategoryNotFoundException();
        }
        dishModel.setCategoryModel(category);
        DishValidation.dishValidate(dishModel);
        dishPersistencePort.createDish(dishModel);
    }

    @Override
    public DishModel getDishById(Long id) {
        DishModel dishModel = dishPersistencePort.getDishById(id);
        DishValidation.getDishValidation(dishModel);
        return dishModel;
    }

    @Override
    public List<DishModel> getPaginatedDishesByCategory(Long id, int page, int size, String category) {
        List<DishModel> dishModelList = dishPersistencePort.getPaginatedDishesByCategory(id, page, size, category);
        DishValidation.getAllDishesValidate(dishModelList);
        return dishModelList;
    }

    @Override
    public DishModel updateDish(Long id, DishModel dishModel) {
        return dishPersistencePort.updateDish(id, dishModel);
    }

    @Override
    public DishModel updateDishStatus(Long id, DishModel dishModel) {
        return dishPersistencePort.updateDishStatus(id, dishModel);
    }
}
