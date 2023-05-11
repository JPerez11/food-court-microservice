package com.pragma.powerup.usermicroservice.domain.validations;

import com.pragma.powerup.usermicroservice.domain.exceptions.RestaurantOwnerIdException;
import com.pragma.powerup.usermicroservice.domain.model.RoleModel;

import static com.pragma.powerup.usermicroservice.configuration.utils.Constants.OWNER_ROLE_NAME;

public class UserValidation {

    private UserValidation() {}
    public static void validateOwner(RoleModel roleModel) {
        if (!OWNER_ROLE_NAME.equals(roleModel.getName())) {
            throw new RestaurantOwnerIdException();
        }
    }
}
