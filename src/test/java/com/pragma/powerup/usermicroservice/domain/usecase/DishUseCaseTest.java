package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.domain.exceptions.ValidationModelException;
import com.pragma.powerup.usermicroservice.domain.model.DishModel;
import com.pragma.powerup.usermicroservice.domain.spi.DishPersistencePort;
import com.pragma.powerup.usermicroservice.domain.usecase.factory.DishTestDataFactory;
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

        //When
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
        assertThrows(NullPointerException.class, () -> {
            dishUseCase.createDish(nullDish);
        });
    }

    @Test
    void shouldThrowValidationModelException() {
        //Given
        DishModel expect = DishTestDataFactory.getEmptyDish();

        //Then
        assertThrows(ValidationModelException.class, () -> {
            dishUseCase.createDish(expect);
        });
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
        assertThrows(ValidationModelException.class, () -> {
            dishUseCase.getDishById(50L);
        });
    }

    @Test
    void shouldGetAllDishes() {
        //Given
        List<DishModel> expected = DishTestDataFactory.getDishesList();

        //When
        Mockito.when(dishPersistencePort.getAllDishes(0)).thenReturn(expected);
        List<DishModel> actual = dishUseCase.getAllDishes(0);

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
        Mockito.when(dishPersistencePort.getAllDishes(0)).thenReturn(expected);

        //Then
        assertThrows(ValidationModelException.class, () -> {
            dishUseCase.getAllDishes(0);
        });
    }

}