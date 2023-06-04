package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.configuration.utils.Constants;
import com.pragma.powerup.usermicroservice.domain.exceptions.NoDataFoundException;
import com.pragma.powerup.usermicroservice.domain.exceptions.RestaurantAlreadyExistsException;
import com.pragma.powerup.usermicroservice.domain.exceptions.RestaurantNotFoundException;
import com.pragma.powerup.usermicroservice.domain.api.RestaurantServicePort;
import com.pragma.powerup.usermicroservice.domain.exceptions.RestaurantOwnerIdException;
import com.pragma.powerup.usermicroservice.domain.exceptions.RoleNotAllowedForCreationException;
import com.pragma.powerup.usermicroservice.domain.exceptions.UserNotFoundException;
import com.pragma.powerup.usermicroservice.domain.fpi.UserFeignClientPort;
import com.pragma.powerup.usermicroservice.domain.model.RestaurantModel;
import com.pragma.powerup.usermicroservice.domain.model.UserModel;
import com.pragma.powerup.usermicroservice.domain.spi.RestaurantPersistencePort;
import com.pragma.powerup.usermicroservice.domain.validations.RestaurantValidation;

import java.util.List;
import java.util.Objects;

import static com.pragma.powerup.usermicroservice.configuration.utils.Constants.OWNER_ROLE_NAME;

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
        if (!Objects.equals(restaurantPersistencePort.getAuthenticatedRole(), Constants.ADMIN_ROLE_NAME)) {
            throw new RoleNotAllowedForCreationException();
        }
        RestaurantValidation.restaurantValidate(restaurantModel);
        if (restaurantPersistencePort.existsRestaurantByTaxIdNumber(restaurantModel.getTaxIdNumber())) {
            throw new RestaurantAlreadyExistsException();
        }
        UserModel userModel = userFeignClientPort.getUserById(restaurantModel.getIdOwner());
        if (userModel == null) {
            throw new UserNotFoundException();
        }
        if (!OWNER_ROLE_NAME.equals(userModel.getRoleModel().getName())) {
            throw new RestaurantOwnerIdException();
        }
        restaurantPersistencePort.createRestaurant(restaurantModel);
    }

    @Override
    public RestaurantModel getRestaurantById(Long id) {
        RestaurantModel restaurantModel = restaurantPersistencePort.getRestaurantById(id);
        if (restaurantModel == null) {
            throw new RestaurantNotFoundException();
        }
        return restaurantModel;
    }

    @Override
    public List<RestaurantModel> getAllRestaurants(int pageNumber, int pageSize) {
        List<RestaurantModel> restaurantModelList = restaurantPersistencePort.getAllRestaurants(pageNumber, pageSize);
        if (restaurantModelList.isEmpty()) {
            throw new NoDataFoundException();
        }
        return restaurantModelList;
    }
}
