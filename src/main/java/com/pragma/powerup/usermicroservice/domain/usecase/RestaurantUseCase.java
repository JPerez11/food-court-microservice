package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.domain.exceptions.NoDataFoundException;
import com.pragma.powerup.usermicroservice.domain.exceptions.RestaurantAlreadyExistsException;
import com.pragma.powerup.usermicroservice.domain.exceptions.RestaurantNotFoundException;
import com.pragma.powerup.usermicroservice.domain.api.RestaurantServicePort;
import com.pragma.powerup.usermicroservice.domain.fpi.UserFeignClientPort;
import com.pragma.powerup.usermicroservice.domain.model.RestaurantModel;
import com.pragma.powerup.usermicroservice.domain.model.UserModel;
import com.pragma.powerup.usermicroservice.domain.spi.RestaurantPersistencePort;
import com.pragma.powerup.usermicroservice.domain.validations.RestaurantValidation;
import com.pragma.powerup.usermicroservice.domain.validations.UserValidation;

import java.util.List;

public class RestaurantUseCase implements RestaurantServicePort {

    private final RestaurantPersistencePort restaurantPersistencePort;
    private final UserFeignClientPort userFeignClientPort;

    public RestaurantUseCase(RestaurantPersistencePort restaurantPersistencePort,
                             UserFeignClientPort userFeignClientPort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.userFeignClientPort = userFeignClientPort;
    }

    @Override
    public void createRestaurant(RestaurantModel restaurantModel) {
        if (restaurantModel == null) {
            throw new NullPointerException();
        }
        if (restaurantPersistencePort.existsRestaurantByTaxIdNumber(restaurantModel.getTaxIdNumber())) {
            throw new RestaurantAlreadyExistsException();
        }
        UserModel userModel = userFeignClientPort.getUserById(restaurantModel.getIdOwner());
        UserValidation.validateOwner(userModel.getRoleModel());
        RestaurantValidation.restaurantValidate(restaurantModel);
        restaurantPersistencePort.createRestaurant(restaurantModel);
    }

    @Override
    public RestaurantModel getRestaurantById(Long id) {
        RestaurantModel restaurantModel = restaurantPersistencePort.getRestaurantById(id);
        if (restaurantModel == null) {
            throw new RestaurantNotFoundException();
        }
        RestaurantValidation.getRestaurantValidate(restaurantModel);
        return restaurantModel;
    }

    @Override
    public List<RestaurantModel> getAllRestaurants(int pageNumber, int pageSize) {
        List<RestaurantModel> restaurantModelList = restaurantPersistencePort.getAllRestaurants(pageNumber, pageSize);
        if (restaurantModelList.isEmpty()) {
            throw new NoDataFoundException();
        }
        RestaurantValidation.getAllRestaurantsValidate(restaurantModelList);
        return restaurantModelList;
    }
}
