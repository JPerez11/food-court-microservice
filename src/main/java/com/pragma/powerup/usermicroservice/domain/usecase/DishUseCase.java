package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.domain.api.DishServicePort;
import com.pragma.powerup.usermicroservice.domain.model.DishModel;
import com.pragma.powerup.usermicroservice.domain.spi.DishPersistencePort;

import java.util.List;

public class DishUseCase implements DishServicePort {

    private final DishPersistencePort dishPersistencePort;

    public DishUseCase(DishPersistencePort dishPersistencePort) {
        this.dishPersistencePort = dishPersistencePort;
    }

    @Override
    public void createDish(DishModel dishModel) {
        dishPersistencePort.createDish(dishModel);
    }

    @Override
    public DishModel getDishById(Long id) {
        DishModel dishModel = dishPersistencePort.getDishById(id);
        return dishModel;
    }

    @Override
    public List<DishModel> getAllDishes(int page) {
        List<DishModel> dishModelList = dishPersistencePort.getAllDishes(page);
        return dishModelList;
    }

    @Override
    public DishModel updateDish(Long id, DishModel dishModel) {
        return dishPersistencePort.updateDish(id, dishModel);
    }
}
