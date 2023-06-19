package com.pragma.powerup.usermicroservice.domain.usecases;

import com.pragma.powerup.usermicroservice.configuration.utils.Constants;
import com.pragma.powerup.usermicroservice.domain.exceptions.NoDataFoundException;
import com.pragma.powerup.usermicroservice.domain.exceptions.RestaurantAlreadyExistsException;
import com.pragma.powerup.usermicroservice.domain.exceptions.RestaurantNotFoundException;
import com.pragma.powerup.usermicroservice.domain.exceptions.RestaurantOwnerIdException;
import com.pragma.powerup.usermicroservice.domain.exceptions.RoleNotAllowedForCreationException;
import com.pragma.powerup.usermicroservice.domain.exceptions.UserNotFoundException;
import com.pragma.powerup.usermicroservice.domain.exceptions.ValidationModelException;
import com.pragma.powerup.usermicroservice.domain.fpi.UserFeignClientPort;
import com.pragma.powerup.usermicroservice.domain.models.RestaurantModel;
import com.pragma.powerup.usermicroservice.domain.models.UserModel;
import com.pragma.powerup.usermicroservice.domain.spi.RestaurantPersistencePort;
import com.pragma.powerup.usermicroservice.domain.usecases.factory.RestaurantTestDataFactory;
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
        Mockito.when(restaurantPersistencePort.getAuthenticatedRole())
                .thenReturn(Constants.ADMIN_ROLE_NAME);
        Mockito.when(restaurantPersistencePort.existsRestaurantByTaxIdNumber(restaurantModel.getTaxIdNumber()))
                .thenReturn(false);
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
        Mockito.when(restaurantPersistencePort.getAuthenticatedRole())
                .thenReturn(Constants.ADMIN_ROLE_NAME);
        Mockito.when(restaurantPersistencePort.existsRestaurantByTaxIdNumber(restaurantModel.getTaxIdNumber()))
                .thenReturn(false);
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
        //Then
        assertThrows(NullPointerException.class,
                () -> restaurantUseCase.createRestaurant(null));
    }

    @Test
    void shouldThrowRoleNotAllowedForCreationException() {
        //Given
        RestaurantModel expectedEmptyRestaurant = RestaurantTestDataFactory.getEmptyRestaurant();

        //When
        Mockito.when(restaurantPersistencePort.getAuthenticatedRole())
                .thenReturn(Constants.OWNER_ROLE_NAME);

        //Then
        assertThrows(RoleNotAllowedForCreationException.class,
                () -> restaurantUseCase.createRestaurant(expectedEmptyRestaurant));
    }

    @Test
    void shouldThrowValidationModelException() {
        //Given
        RestaurantModel expectedEmptyRestaurant = RestaurantTestDataFactory.getEmptyRestaurant();

        //When
        Mockito.when(restaurantPersistencePort.getAuthenticatedRole())
                .thenReturn(Constants.ADMIN_ROLE_NAME);

        //Then
        assertThrows(ValidationModelException.class,
                () -> restaurantUseCase.createRestaurant(expectedEmptyRestaurant));

    }

    @Test
    void shouldThrowRestaurantAlreadyExistsException() {
        //Given
        RestaurantModel expected = RestaurantTestDataFactory.getRestaurantFromSetters();

        //When
        Mockito.when(restaurantPersistencePort.getAuthenticatedRole())
                .thenReturn(Constants.ADMIN_ROLE_NAME);
        Mockito.when(restaurantPersistencePort.existsRestaurantByTaxIdNumber(expected.getTaxIdNumber()))
                .thenReturn(true);
        //Then
        assertThrows(RestaurantAlreadyExistsException.class,
                () -> restaurantUseCase.createRestaurant(expected));
    }

    @Test
    void shouldThrowUserNorFoundException() {
        //Given
        RestaurantModel expected = RestaurantTestDataFactory.getRestaurantFromSetters();

        //When
        Mockito.when(restaurantPersistencePort.getAuthenticatedRole())
                .thenReturn(Constants.ADMIN_ROLE_NAME);
        Mockito.when(restaurantPersistencePort.existsRestaurantByTaxIdNumber(expected.getTaxIdNumber()))
                .thenReturn(false);
        Mockito.when(userFeignClientPort.getUserById(expected.getIdOwner())).thenReturn(null);
        //Then
        assertThrows(UserNotFoundException.class,
                () -> restaurantUseCase.createRestaurant(expected));
    }

    @Test
    void shouldThrowRestaurantOwnerIdException() {
        //Given
        RestaurantModel differentIdOwner = RestaurantTestDataFactory.getRestaurantWithDifferentIdOwner();
        UserModel userAdmin = RestaurantTestDataFactory.getUserAdminFromSetters();

        //When
        Mockito.when(restaurantPersistencePort.getAuthenticatedRole())
                .thenReturn(Constants.ADMIN_ROLE_NAME);
        Mockito.when(restaurantPersistencePort.existsRestaurantByTaxIdNumber(differentIdOwner.getTaxIdNumber()))
                .thenReturn(false);
        Mockito.when(userFeignClientPort.getUserById(differentIdOwner.getIdOwner())).thenReturn(userAdmin);

        //Then
        assertThrows(RestaurantOwnerIdException.class,
                () -> restaurantUseCase.createRestaurant(differentIdOwner));
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

    }

    @Test
    void shouldThrowRestaurantNotFoundException() {
        assertThrows(RestaurantNotFoundException.class,
                () -> restaurantUseCase.getRestaurantById(10L));
    }

    @Test
    void shouldGetAllRestaurants() {
        //Given
        List<RestaurantModel> restaurantListExpected = RestaurantTestDataFactory.getRestaurantList();

        //When
        when(restaurantPersistencePort.getAllRestaurants(0, 2)).thenReturn(restaurantListExpected);
        List<RestaurantModel> result = restaurantUseCase.getAllRestaurants(0, 2);

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
        verify(restaurantPersistencePort).getAllRestaurants(0, 2);
    }

    @Test
    void shouldThrowNoDataFoundException() {
        //Given
        List<RestaurantModel> expectedEmptyList = RestaurantTestDataFactory.getEmptyRestaurantList();

        //When
        Mockito.when(restaurantPersistencePort.getAllRestaurants(0, 2)).thenReturn(expectedEmptyList);

        //Then
        assertThrows(NoDataFoundException.class,
                () -> restaurantUseCase.getAllRestaurants(0, 2));
    }


}