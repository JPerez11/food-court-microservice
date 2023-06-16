package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.configuration.utils.Constants;
import com.pragma.powerup.usermicroservice.domain.api.RestaurantEmployeeServicePort;
import com.pragma.powerup.usermicroservice.domain.exceptions.EmployeeAlreadyAssignedException;
import com.pragma.powerup.usermicroservice.domain.exceptions.RestaurantNotFoundException;
import com.pragma.powerup.usermicroservice.domain.exceptions.RoleNotAllowedForCreationException;
import com.pragma.powerup.usermicroservice.domain.exceptions.UserHasNoEmployeeRoleException;
import com.pragma.powerup.usermicroservice.domain.exceptions.UserNotFoundException;
import com.pragma.powerup.usermicroservice.domain.fpi.UserFeignClientPort;
import com.pragma.powerup.usermicroservice.domain.model.RestaurantEmployeeModel;
import com.pragma.powerup.usermicroservice.domain.model.UserModel;
import com.pragma.powerup.usermicroservice.domain.spi.RestaurantEmployeePersistencePort;

import java.util.Objects;

import static com.pragma.powerup.usermicroservice.configuration.utils.Constants.EMPLOYEE_ROLE_NAME;

public class RestaurantEmployeeUseCase implements RestaurantEmployeeServicePort {

    private final RestaurantEmployeePersistencePort persistencePort;
    private final UserFeignClientPort userFeignClientPort;

    public RestaurantEmployeeUseCase(RestaurantEmployeePersistencePort persistencePort, UserFeignClientPort userFeignClientPort) {
        this.persistencePort = persistencePort;
        this.userFeignClientPort = userFeignClientPort;
    }

    @Override
    public void assignEmployee(RestaurantEmployeeModel restaurantEmployeeModel) {
        if (restaurantEmployeeModel == null) {
            throw new NullPointerException();
        }
        if (!Objects.equals(persistencePort.getAuthenticatedRole(), Constants.OWNER_ROLE_NAME)) {
            throw new RoleNotAllowedForCreationException();
        }
        Long employeeId = restaurantEmployeeModel.getEmployeeId();
        UserModel userModel = userFeignClientPort.getUserById(employeeId);
        if (userModel == null) {
            throw new UserNotFoundException();
        }
        if (!EMPLOYEE_ROLE_NAME.equals(userModel.getRoleModel().getName())) {
            throw new UserHasNoEmployeeRoleException();
        }
        if (persistencePort.existsAssignedEmployee(employeeId)) {
            throw new EmployeeAlreadyAssignedException();
        }
        Long restaurantId = restaurantEmployeeModel.getRestaurantModel().getId();
        if (!persistencePort.existsRestaurant(restaurantId)) {
            throw new RestaurantNotFoundException();
        }
        persistencePort.assignEmployee(restaurantEmployeeModel);
    }

}
