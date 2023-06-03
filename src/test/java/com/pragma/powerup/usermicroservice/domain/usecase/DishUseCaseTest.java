package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.domain.exceptions.CategoryNotFoundException;
import com.pragma.powerup.usermicroservice.domain.exceptions.DishNotFoundException;
import com.pragma.powerup.usermicroservice.domain.exceptions.OwnerNotAuthorizedForUpdateException;
import com.pragma.powerup.usermicroservice.domain.exceptions.RestaurantNotFoundException;
import com.pragma.powerup.usermicroservice.domain.exceptions.ValidationModelException;
import com.pragma.powerup.usermicroservice.domain.model.CategoryModel;
import com.pragma.powerup.usermicroservice.domain.model.DishModel;
import com.pragma.powerup.usermicroservice.domain.model.RestaurantModel;
import com.pragma.powerup.usermicroservice.domain.spi.DishPersistencePort;
import com.pragma.powerup.usermicroservice.domain.usecase.factory.DishTestDataFactory;
import com.pragma.powerup.usermicroservice.domain.usecase.factory.RestaurantTestDataFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DishUseCaseTest {

    @InjectMocks
    DishUseCase dishUseCase;

    @Mock
    DishPersistencePort dishPersistencePort;

    @Test
    void shouldCreateDish() {
        //Given
        DishModel expect = DishTestDataFactory.getDishFromSetters();
        RestaurantModel restaurant = RestaurantTestDataFactory.getRestaurantFromSetters();
        CategoryModel category = DishTestDataFactory.getCategoryFromSetters();

        //When
        Mockito.when(dishPersistencePort.getRestaurantById(1L))
                .thenReturn(restaurant);
        expect.setRestaurantModel(restaurant);
        Mockito.when(dishPersistencePort.getAuthenticatedUserId())
                .thenReturn(2L);
        Mockito.when(dishPersistencePort.getCategoryById(1L))
                .thenReturn(category);
        expect.setCategoryModel(category);
        Mockito.doNothing().when(dishPersistencePort).createDish(expect);
        dishUseCase.createDish(expect);

        //Then
        Mockito.verify(dishPersistencePort).createDish(expect);
    }

    @Test
    void shouldThrowNullPointerException() {
        //Given
        DishModel nullDish = new DishModel();


        //Then
        assertThrows(NullPointerException.class,
                () -> dishUseCase.createDish(nullDish));
    }

    @Test
    void shouldThrowValidationModelException() {
        //Given
        DishModel expect = DishTestDataFactory.getEmptyDish();

        //Then
        assertThrows(ValidationModelException.class, () ->
            dishUseCase.createDish(expect));
    }

    @Test
    void shouldThrowRestaurantNotFoundException() {
        // Given
        DishModel dishModel = DishTestDataFactory.getDishFromSetters();

        // When
        Mockito.when(dishPersistencePort.getRestaurantById(1L))
                .thenReturn(null);

        // Then throw
        assertThrows(RestaurantNotFoundException.class,
                () -> dishUseCase.createDish(dishModel));
    }

    @Test
    void shouldThrowOwnerNotAuthorizedForUpdateException() {
        // Given
        DishModel dishModel = DishTestDataFactory.getDishFromSetters();
        RestaurantModel restaurant = RestaurantTestDataFactory.getRestaurantFromSetters();

        // When
        Mockito.when(dishPersistencePort.getRestaurantById(1L))
                .thenReturn(restaurant);
        dishModel.setRestaurantModel(restaurant);

        // Then throw
        assertThrows(OwnerNotAuthorizedForUpdateException.class,
                () -> dishUseCase.createDish(dishModel));
    }

    @Test
    void shouldThrowCategoryNotFoundException() {
        // Given
        DishModel dishModel = DishTestDataFactory.getDishFromSetters();
        RestaurantModel restaurant = RestaurantTestDataFactory.getRestaurantFromSetters();

        // When
        Mockito.when(dishPersistencePort.getRestaurantById(1L))
                .thenReturn(restaurant);
        dishModel.setRestaurantModel(restaurant);
        Mockito.when(dishPersistencePort.getAuthenticatedUserId())
                .thenReturn(2L);
        Mockito.when(dishPersistencePort.getCategoryById(1L))
                .thenReturn(null);

        // Then throw
        assertThrows(CategoryNotFoundException.class,
                () -> dishUseCase.createDish(dishModel));
    }

    @Test
    void shouldGetDishById() {
        //Given
        DishModel expect = DishTestDataFactory.getDishFromConstructor();

        //When
        Mockito.when(dishPersistencePort.getDishById(2L)).thenReturn(expect);
        dishUseCase.getDishById(2L);

        //Then
        Mockito.verify(dishPersistencePort).getDishById(Mockito.anyLong());
    }

    @Test
    void shouldThrowDishNotFoundException() {
        //Then
        assertThrows(DishNotFoundException.class,
                () -> dishUseCase.getDishById(50L));
    }

    @Test
    void getPaginatedDishesByCategory() {
        //Given
        List<DishModel> expected = DishTestDataFactory.getDishesList();

        //When
        Mockito.when(dishPersistencePort.getPaginatedDishesByCategory(1L,0, 10, "category")).thenReturn(expected);
        List<DishModel> actual = dishUseCase.getPaginatedDishesByCategory(1L,0, 10, "category");

        //Then
        assertEquals(expected, actual);
        assertEquals(expected.get(0).getId(), actual.get(0).getId());
        assertEquals(expected.get(0).isActive(), actual.get(0).isActive());
        assertEquals(expected.get(0).getCategoryModel().getId(),
                actual.get(0).getCategoryModel().getId());
        assertEquals(expected.get(0).getCategoryModel().getName(), actual.
                get(0).getCategoryModel().getName());
        assertEquals(expected.get(0).getCategoryModel().getDescription(),
                actual.get(0).getCategoryModel().getDescription());

    }

    @Test
    void shouldThrowValidationModelExceptionInGetAllDishes() {
        //Given
        List<DishModel> expected = Collections.emptyList();

        //When
        Mockito.when(dishPersistencePort.getPaginatedDishesByCategory(1L, 0, 10, "category")).thenReturn(expected);

        //Then
        assertThrows(ValidationModelException.class, () -> {
            dishUseCase.getPaginatedDishesByCategory(1L, 0, 10, "category");
        });
    }

    @Test
    void shouldUpdateDish() {
        //Given
        DishModel expect = DishTestDataFactory.getDishFromSetters();

        //When
        Mockito.when(dishPersistencePort.getDishById(1L)).thenReturn(expect);
        Mockito.when(dishPersistencePort.getAuthenticatedUserId()).thenReturn(2L);
        Mockito.when(dishPersistencePort.updateDish(1L, expect)).thenReturn(expect);
        dishUseCase.updateDish(1L, expect);

        //Then
        Mockito.verify(dishPersistencePort).updateDish(1L, expect);

    }

    @Test
    void shouldThrowNullPointerExceptionInUpdateDish() {
        //Then
        assertThrows(NullPointerException.class,
                () -> dishUseCase.updateDish(1L, null));
    }

    @Test
    void shouldThrowDishNotFoundExceptionInUpdateDish() {
        //Given
        DishModel nullDish = new DishModel();

        //Then
        assertThrows(DishNotFoundException.class,
                () -> dishUseCase.updateDish(1L, nullDish));
    }

    @Test
    void shouldThrowOwnerNotAuthorizedForUpdateExceptionInUpdateDish() {
        //Given
        DishModel expect = DishTestDataFactory.getDishFromSetters();

        //When
        Mockito.when(dishPersistencePort.getDishById(1L)).thenReturn(expect);

        //Then
        assertThrows(OwnerNotAuthorizedForUpdateException.class,
                () -> dishUseCase.updateDish(1L, expect));
    }

    @Test
    void shouldUpdateDishStatus() {
        //Given
        DishModel expect = DishTestDataFactory.getDishFromSetters();

        //When
        Mockito.when(dishPersistencePort.updateDishStatus(1L, expect)).thenReturn(expect);
        dishUseCase.updateDishStatus(1L, expect);

        //Then
        Mockito.verify(dishPersistencePort).updateDishStatus(1L, expect);

    }

}