package com.pragma.powerup.usermicroservice.configuration;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.CategoryNotFoundException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.DishAlreadyExistsException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.DishNotFoundException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.NoDataFoundException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.OrderAlreadyExistsException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.OwnerNotAuthorizedForUpdateException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.RestaurantAlreadyExistsException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.RestaurantNotFoundException;
import com.pragma.powerup.usermicroservice.domain.exceptions.RestaurantOwnerIdException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.RoleNotAllowedForCreationException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.RoleNotFoundException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.UserNotFoundException;
import com.pragma.powerup.usermicroservice.domain.exceptions.ValidationModelException;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.pragma.powerup.usermicroservice.configuration.utils.Constants.CATEGORY_NOT_FOUND_MESSAGE;
import static com.pragma.powerup.usermicroservice.configuration.utils.Constants.DISH_ALREADY_EXISTS_MESSAGE;
import static com.pragma.powerup.usermicroservice.configuration.utils.Constants.DISH_NOT_FOUND_MESSAGE;
import static com.pragma.powerup.usermicroservice.configuration.utils.Constants.NO_DATA_FOUND_MESSAGE;
import static com.pragma.powerup.usermicroservice.configuration.utils.Constants.ORDER_ALREADY_EXISTS_MESSAGE;
import static com.pragma.powerup.usermicroservice.configuration.utils.Constants.OWNER_NOT_AUTHORIZED_FOR_UPDATE_MESSAGE;
import static com.pragma.powerup.usermicroservice.configuration.utils.Constants.RESPONSE_ERROR_MESSAGE_KEY;
import static com.pragma.powerup.usermicroservice.configuration.utils.Constants.RESTAURANT_ALREADY_EXISTS_MESSAGE;
import static com.pragma.powerup.usermicroservice.configuration.utils.Constants.RESTAURANT_NOT_FOUND_MESSAGE;
import static com.pragma.powerup.usermicroservice.configuration.utils.Constants.RESTAURANT_OWNER_ID_MESSAGE;
import static com.pragma.powerup.usermicroservice.configuration.utils.Constants.ROLE_NOT_ALLOWED_MESSAGE;
import static com.pragma.powerup.usermicroservice.configuration.utils.Constants.ROLE_NOT_FOUND_MESSAGE;
import static com.pragma.powerup.usermicroservice.configuration.utils.Constants.USER_NOT_FOUND_MESSAGE;
import static com.pragma.powerup.usermicroservice.configuration.utils.Constants.WRONG_CREDENTIALS_MESSAGE;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errorMessages = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            if (error instanceof FieldError fieldError) {
                errorMessages.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
            } else {
                errorMessages.add(error.getDefaultMessage());
            }
        }
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, String>> handleAuthenticationException(AuthenticationException noDataFoundException) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, WRONG_CREDENTIALS_MESSAGE));
    }

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoDataFoundException(NoDataFoundException noDataFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, NO_DATA_FOUND_MESSAGE));
    }
    @ExceptionHandler(RoleNotAllowedForCreationException.class)
    public ResponseEntity<Map<String, String>> handleRoleNotAllowedForCreationException(
            RoleNotAllowedForCreationException roleNotAllowedForCreationException) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, ROLE_NOT_ALLOWED_MESSAGE));
    }
    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleRoleNotFoundException(
            RoleNotFoundException roleNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, ROLE_NOT_FOUND_MESSAGE));
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(
            UserNotFoundException userNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, USER_NOT_FOUND_MESSAGE));
    }
    @ExceptionHandler(RestaurantAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleRestaurantAlreadyExistsException(
            RestaurantAlreadyExistsException restaurantAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, RESTAURANT_ALREADY_EXISTS_MESSAGE));
    }
    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleRestaurantNotFoundException(
            RestaurantNotFoundException restaurantNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, RESTAURANT_NOT_FOUND_MESSAGE));
    }
    @ExceptionHandler(RestaurantOwnerIdException.class)
    public ResponseEntity<Map<String, String>> handleRestaurantOwnerIdException(
            RestaurantOwnerIdException restaurantOwnerIdException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, RESTAURANT_OWNER_ID_MESSAGE));
    }
    @ExceptionHandler(DishAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleDishAlreadyExistsException(
            DishAlreadyExistsException dishAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, DISH_ALREADY_EXISTS_MESSAGE));
    }
    @ExceptionHandler(DishNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleDishNotFoundException(
            DishNotFoundException dishNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, DISH_NOT_FOUND_MESSAGE));
    }
    @ExceptionHandler(OwnerNotAuthorizedForUpdateException.class)
    public ResponseEntity<Map<String, String>> handleOwnerNotAuthorizedForUpdateException(
            OwnerNotAuthorizedForUpdateException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, OWNER_NOT_AUTHORIZED_FOR_UPDATE_MESSAGE));
    }
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCategoryNotFoundException(
            CategoryNotFoundException categoryNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, CATEGORY_NOT_FOUND_MESSAGE));
    }
    @ExceptionHandler(ValidationModelException.class)
    public ResponseEntity<Map<String, Map<String, String>>> handleValidationModelException(
            ValidationModelException domainException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, domainException.getException()));
    }
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<String> handleFeignException(FeignException feignException) {
        return ResponseEntity.status(feignException.status())
                .body(feignException.contentUTF8());
    }
    @ExceptionHandler(OrderAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleOrderAlreadyExistsException(
            OrderAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, ORDER_ALREADY_EXISTS_MESSAGE));
    }
}
