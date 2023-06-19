package com.pragma.powerup.usermicroservice.domain.usecases.factory;

import com.pragma.powerup.usermicroservice.domain.models.RestaurantModel;
import com.pragma.powerup.usermicroservice.domain.models.RoleModel;
import com.pragma.powerup.usermicroservice.domain.models.UserModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RestaurantTestDataFactory {

    private RestaurantTestDataFactory() {}

    public static RestaurantModel getRestaurantFromSetters() {
        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setId(1L);
        restaurantModel.setName("restaurant1");
        restaurantModel.setTaxIdNumber("1234567890");
        restaurantModel.setAddress("test address");
        restaurantModel.setPhoneNumber("1234567890");
        restaurantModel.setLogoUrl("https://logo-url-test.com");
        restaurantModel.setIdOwner(2L);

        return restaurantModel;
    }
    public static RestaurantModel getRestaurantWithoutOwner() {
        return new RestaurantModel(
                2L,
                "restaurant2",
                "12345678902",
                "test address 2",
                "+12345678902",
                "https://logo-url2-test.com",
                0L
        );
    }
    public static RestaurantModel getEmptyRestaurant() {
        return new RestaurantModel(
                0L,
                "",
                "",
                "",
                "",
                "",
                2L
        );
    }
    public static RestaurantModel getRestaurantWithDifferentIdOwner() {
        RestaurantModel restaurantModel = getRestaurantFromSetters();
        restaurantModel.setIdOwner(1L);

        return restaurantModel;
    }
    public static List<RestaurantModel> getRestaurantList() {
        List<RestaurantModel> restaurantModelList = new ArrayList<>();
        RestaurantModel restaurantModel = getRestaurantWithoutOwner();
        restaurantModel.setIdOwner(2L);
        restaurantModelList.add(getRestaurantFromSetters());
        restaurantModelList.add(restaurantModel);
        return restaurantModelList;
    }

    public static List<RestaurantModel> getEmptyRestaurantList() {
        return Collections.emptyList();
    }

    public static UserModel getUserAdminFromSetters() {
        UserModel userModel = new UserModel();
        userModel.setId(1L);
        userModel.setFirstName("Admin");
        userModel.setLastName("Admin");
        userModel.setDocumentNumber("1234567890");
        userModel.setPhoneNumber("+571234567890");
        userModel.setEmail("admin@gmail.com");
        userModel.setPassword("admin123");
        userModel.setBirthdate(LocalDate.of(2000, 1, 1));
        userModel.setRoleModel(getRoleAdmin());

        return userModel;
    }
    public static UserModel getUserOwnerFromSetters() {
        return new UserModel(
                2L,
                "Owner",
                "Owner",
                "1234567890",
                "+571234567890",
                LocalDate.of(2000, 1, 1),
                "owner@gmail.com",
                "owner123",
                getRoleOwner());
    }

    public static RoleModel getRoleAdmin() {
            return new RoleModel(1L, "ADMIN", "PLACE MANAGER");
    }

    public static RoleModel getRoleOwner() {
        RoleModel roleModel = new RoleModel();

        roleModel.setId(2L);
        roleModel.setName("OWNER");
        roleModel.setDescription("ROLE_OWNER");
        return  roleModel;
    }

}
