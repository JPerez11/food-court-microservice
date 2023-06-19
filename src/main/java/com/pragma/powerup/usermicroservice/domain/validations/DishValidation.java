package com.pragma.powerup.usermicroservice.domain.validations;

import com.pragma.powerup.usermicroservice.domain.exceptions.ValidationModelException;
import com.pragma.powerup.usermicroservice.domain.models.CategoryModel;
import com.pragma.powerup.usermicroservice.domain.models.DishModel;
import com.pragma.powerup.usermicroservice.domain.models.RestaurantModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DishValidation {

    private static Map<String, String> exceptionMap;
    private static final String EMPTY_FIELD = "This field cannot empty";
    private static final String INVALID_PRICE_FORMAT = "The price must be an integer";

    private DishValidation() {}

    public static void dishValidate(DishModel dishModel) {
        exceptionMap = new HashMap<>();

        validateString(dishModel.getName(), "name");
        validateString(dishModel.getDescription(), "description");
        validateString(dishModel.getImageUrl(), "url");
        validateString(String.valueOf(dishModel.getPrice()), "url");
        validateCategory(dishModel.getCategoryModel());
        validateRestaurant(dishModel.getRestaurantModel());
        validatePrice(dishModel.getPrice());

        if (!exceptionMap.isEmpty()) {
            throw new ValidationModelException(exceptionMap);
        }

    }

    private static void validateString(String field, String fieldName) {
        if (isStringEmpty(field)) {
            exceptionMap.put(fieldName, EMPTY_FIELD);
        }
    }

    private static void validatePrice(int field) {
        if (field <= 0) {
            exceptionMap.put("price", INVALID_PRICE_FORMAT);
        }
    }

    private static void validateCategory(CategoryModel categoryModel) {
        if (categoryModel.getId() == null) {
            exceptionMap.put("category", EMPTY_FIELD);
        }
    }

    private static void validateRestaurant(RestaurantModel restaurantModel) {
        if (restaurantModel.getId() == null) {
            exceptionMap.put("restaurant", EMPTY_FIELD);
        }
    }

    private static boolean isStringEmpty(String data) {
        return data.trim().isEmpty();
    }

    public static void getAllDishesValidate(List<DishModel> dishModelList) {
        exceptionMap = new HashMap<>();
        exceptionMap.put("message", "No data found in the database");
        if (dishModelList.isEmpty()) {
            throw new ValidationModelException(exceptionMap);
        }
    }
}
