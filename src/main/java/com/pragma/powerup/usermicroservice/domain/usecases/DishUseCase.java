package com.pragma.powerup.usermicroservice.domain.usecases;

import com.pragma.powerup.usermicroservice.domain.api.DishServicePort;
import com.pragma.powerup.usermicroservice.domain.exceptions.CategoryNotFoundException;
import com.pragma.powerup.usermicroservice.domain.exceptions.DishNotFoundException;
import com.pragma.powerup.usermicroservice.domain.exceptions.OwnerNotAuthorizedForUpdateException;
import com.pragma.powerup.usermicroservice.domain.exceptions.RestaurantNotFoundException;
import com.pragma.powerup.usermicroservice.domain.models.CategoryModel;
import com.pragma.powerup.usermicroservice.domain.models.DishModel;
import com.pragma.powerup.usermicroservice.domain.models.RestaurantModel;
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
        DishValidation.dishValidate(dishModel);
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
                dishModel.getCategoryModel().getId());
        if (category == null) {
            throw new CategoryNotFoundException();
        }
        dishModel.setCategoryModel(category);
        dishPersistencePort.createDish(dishModel);
    }

    @Override
    public DishModel getDishById(Long id) {
        DishModel dishModel = dishPersistencePort.getDishById(id);
        if (dishModel == null) {
            throw new DishNotFoundException();
        }
        return dishModel;
    }

    @Override
    public List<DishModel> getPaginatedDishesByCategory(Long id, int page, int size, String category) {
        if (!dishPersistencePort.existsCategoryByName(category)) {
            throw new CategoryNotFoundException();
        }
        if (!dishPersistencePort.existsRestaurantById(id)) {
            throw new RestaurantNotFoundException();
        }
        List<DishModel> dishModelList = dishPersistencePort.getPaginatedDishesByCategory(id, page, size, category);
        DishValidation.getAllDishesValidate(dishModelList);
        return dishModelList;
    }

    @Override
    public DishModel updateDish(Long id, DishModel dishModel) {
        DishModel dishDb = dishToUpdate(id, dishModel);
        dishDb.setDescription( dishModel.getDescription() );
        dishDb.setPrice( dishModel.getPrice() );
        return dishPersistencePort.updateDish(id, dishDb);
    }

    @Override
    public DishModel updateDishStatus(Long id, DishModel dishModel) {
        DishModel dishDb = dishToUpdate(id, dishModel);
        dishDb.setActive( dishModel.isActive() );
        return dishPersistencePort.updateDish(id, dishDb);
    }

    private DishModel dishToUpdate(Long id, DishModel dishModel) {
        if (dishModel == null) {
            throw new NullPointerException();
        }
        DishModel dishDb = dishPersistencePort.getDishById(id);
        if (dishDb == null) {
            throw new DishNotFoundException();
        }
        Long authenticatedUserId = dishPersistencePort.getAuthenticatedUserId();
        if (!Objects.equals(dishDb.getRestaurantModel().getIdOwner(), authenticatedUserId)) {
            throw new OwnerNotAuthorizedForUpdateException();
        }
        return dishDb;
    }
}
