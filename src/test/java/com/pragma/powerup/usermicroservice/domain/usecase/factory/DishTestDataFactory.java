package com.pragma.powerup.usermicroservice.domain.usecase.factory;

import com.pragma.powerup.usermicroservice.domain.model.CategoryModel;
import com.pragma.powerup.usermicroservice.domain.model.DishModel;
import com.pragma.powerup.usermicroservice.domain.model.RestaurantModel;

import java.util.ArrayList;
import java.util.List;

public class DishTestDataFactory {

    private DishTestDataFactory() {}

    public static DishModel getDishFromSetters() {

        DishModel dishModel = new DishModel();
        dishModel.setId(1L);
        dishModel.setName("dish1");
        dishModel.setDescription("dishDescription");
        dishModel.setPrice(10_000);
        dishModel.setImageUrl("https://image_url_test.com");
        dishModel.setActive(true);
        dishModel.setRestaurantModel(RestaurantTestDataFactory.getRestaurantFromSetters());
        dishModel.setCategoryModel(getCategoryFromSetters());

        return dishModel;
    }

    public static DishModel getDishFromConstructor() {
        return new DishModel(
                2L,
                "dish2",
                "dish2Description",
                10_000,
                "https://image_url2_test.com",
                true,
                RestaurantTestDataFactory.getRestaurantWithDifferentIdOwner(),
                getCategoryFromConstructor()
        );
    }

    public static DishModel getEmptyDish() {
        return new DishModel(
                0L,
                "",
                "",
                0,
                "",
                false,
                new RestaurantModel(),
                new CategoryModel()
        );
    }

    private static CategoryModel getCategoryFromSetters() {
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setId(1L);
        categoryModel.setName("category1");
        categoryModel.setDescription("categoryDescription");

        return categoryModel;
    }

    private static CategoryModel getCategoryFromConstructor() {
        return new CategoryModel(
                2L,
                "category2",
                "categoryDescription");
    }

    public static List<DishModel> getDishesList() {
        List<DishModel> list = new ArrayList<>();
        list.add(getDishFromConstructor());
        list.add(getDishFromSetters());

        return list;
    }
}
