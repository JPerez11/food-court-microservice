package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.domain.exceptions.RestaurantOwnerIdException;
import com.pragma.powerup.usermicroservice.domain.exceptions.ValidationModelException;
import com.pragma.powerup.usermicroservice.domain.fpi.UserFeignClientPort;
import com.pragma.powerup.usermicroservice.domain.model.RestaurantModel;
import com.pragma.powerup.usermicroservice.domain.model.UserModel;
import com.pragma.powerup.usermicroservice.domain.spi.RestaurantPersistencePort;
import com.pragma.powerup.usermicroservice.domain.usecase.factory.RestaurantTestDataFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantUseCaseTest {

    @InjectMocks
    RestaurantUseCase restaurantUseCase;

    @Mock
    RestaurantPersistencePort restaurantPersistencePort;
    @Mock
    UserFeignClientPort userFeignClientPort;

    @Test
    void shouldCreateRestaurant() {
        //Given
        RestaurantModel restaurantModel = RestaurantTestDataFactory.getRestaurantFromSetters();
        UserModel userModel = RestaurantTestDataFactory.getUserOwnerFromSetters();

        //When
        Mockito.when(userFeignClientPort.getUserById(restaurantModel.getIdOwner())).thenReturn(userModel);
        Mockito.doNothing().when(restaurantPersistencePort).createRestaurant(restaurantModel);
        restaurantUseCase.createRestaurant(restaurantModel);

        //Then
        Mockito.verify(restaurantPersistencePort).createRestaurant(restaurantModel);
    }

    @Test
    void shouldAssertTheUserAndRoleObtained() {
        //Given
        RestaurantModel restaurantModel = RestaurantTestDataFactory.getRestaurantFromSetters();
        UserModel userModel = RestaurantTestDataFactory.getUserOwnerFromSetters();

        //When
        Mockito.when(userFeignClientPort.getUserById(restaurantModel.getIdOwner())).thenReturn(userModel);
        Mockito.doNothing().when(restaurantPersistencePort).createRestaurant(restaurantModel);
        restaurantUseCase.createRestaurant(restaurantModel);

        //Then
        assertNotNull(userModel.getId());
        assertNotNull(userModel.getFirstName());
        assertNotNull(userModel.getLastName());
        assertNotNull(userModel.getEmail());
        assertNotNull(userModel.getDocumentNumber());
        assertNotNull(userModel.getPhoneNumber());
        assertNotNull(userModel.getBirthdate());
        assertNotNull(userModel.getPassword());
        assertNotNull(userModel.getRoleModel());
        assertNotNull(userModel.getRoleModel().getId());
        assertNotNull(userModel.getRoleModel().getName());
        assertNotNull(userModel.getRoleModel().getDescription());

    }

    @Test
    void shouldThrowNullPointerException() {
        //Given
        RestaurantModel withoutOwner = RestaurantTestDataFactory.getRestaurantWithoutOwner();

        //Then
        assertThrows(NullPointerException.class, () -> {
            restaurantUseCase.createRestaurant(withoutOwner);
        });
    }

    @Test
    void shouldThrowValidationModelException() {
        //Given
        RestaurantModel expectedEmptyRestaurant = RestaurantTestDataFactory.getEmptyRestaurant();
        UserModel userModel = RestaurantTestDataFactory.getUserOwnerFromSetters();

        //When
        Mockito.when(userFeignClientPort.getUserById(expectedEmptyRestaurant.getIdOwner()))
                .thenReturn(userModel);

        //Then
        assertThrows(ValidationModelException.class, () -> {
            restaurantUseCase.createRestaurant(expectedEmptyRestaurant);
        });

    }

    @Test
    void shouldThrowRestaurantOwnerIdException() {
        //Given
        RestaurantModel differentIdOwner = RestaurantTestDataFactory.getRestaurantWithDifferentIdOwner();
        UserModel userAdmin = RestaurantTestDataFactory.getUserAdminFromSetters();

        //When
        Mockito.when(userFeignClientPort.getUserById(differentIdOwner.getIdOwner())).thenReturn(userAdmin);

        //Then
        assertThrows(RestaurantOwnerIdException.class, () -> {
            restaurantUseCase.createRestaurant(differentIdOwner);
        });
    }

    @Test
    void shouldGetRestaurantById() {
        //Given
        RestaurantModel restaurantExpected = RestaurantTestDataFactory.getRestaurantFromSetters();

        //When
        when(restaurantPersistencePort.getRestaurantById(1L)).thenReturn(restaurantExpected);
        restaurantUseCase.getRestaurantById(1L);

        //Then
        verify(restaurantPersistencePort).getRestaurantById(1L);
        assertThrows(ValidationModelException.class, () -> {
            restaurantUseCase.getRestaurantById(10L);
        });

    }

    @Test
    void shouldGetAllRestaurants() {
        //Given
        List<RestaurantModel> restaurantListExpected = RestaurantTestDataFactory.getRestaurantList();

        //When
        when(restaurantPersistencePort.getAllRestaurants()).thenReturn(restaurantListExpected);
        List<RestaurantModel> result = restaurantUseCase.getAllRestaurants();

        //Then
        assertEquals(restaurantListExpected, result);
        assertEquals(restaurantListExpected.get(0).getId(), result.get(0).getId());
        assertEquals(restaurantListExpected.get(0).getName(), result.get(0).getName());
        assertEquals(restaurantListExpected.get(0).getTaxIdNumber(), result.get(0).getTaxIdNumber());
        assertEquals(restaurantListExpected.get(0).getPhoneNumber(), result.get(0).getPhoneNumber());
        assertEquals(restaurantListExpected.get(0).getAddress(), result.get(0).getAddress());
        assertEquals(restaurantListExpected.get(0).getLogoUrl(), result.get(0).getLogoUrl());
        assertEquals(restaurantListExpected.get(0).getIdOwner(), result.get(0).getIdOwner());

        //Verify
        verify(restaurantPersistencePort).getAllRestaurants();
    }

    @Test
    void shouldThrowValidationModelExceptionInGetAllRestaurants() {
        //Given
        List<RestaurantModel> expectedEmptyList = RestaurantTestDataFactory.getEmptyRestaurantList();

        //When
        Mockito.when(restaurantPersistencePort.getAllRestaurants()).thenReturn(expectedEmptyList);

        //Then
        assertThrows(ValidationModelException.class, () -> {
            restaurantUseCase.getAllRestaurants();
        });
    }


}