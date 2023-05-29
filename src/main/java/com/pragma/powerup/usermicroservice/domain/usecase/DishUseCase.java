package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.domain.api.DishServicePort;
import com.pragma.powerup.usermicroservice.domain.model.DishModel;
import com.pragma.powerup.usermicroservice.domain.spi.DishPersistencePort;
import com.pragma.powerup.usermicroservice.domain.validations.DishValidation;

import java.util.List;

public class DishUseCase implements DishServicePort {

    private final DishPersistencePort dishPersistencePort;

    public DishUseCase(DishPersistencePort dishPersistencePort) {
        this.dishPersistencePort = dishPersistencePort;
    }

    @Override
    public void createDish(DishModel dishModel) {
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
    public List<DishModel> getAllDishes(int page) {
        List<DishModel> dishModelList = dishPersistencePort.getAllDishes(page);
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
