package com.pragma.powerup.usermicroservice.configuration;

import com.pragma.powerup.usermicroservice.adapters.driven.feign.exceptions.TwilioFeignClientException;
import com.pragma.powerup.usermicroservice.configuration.utils.Constants;
import com.pragma.powerup.usermicroservice.domain.exceptions.CategoryNotFoundException;
import com.pragma.powerup.usermicroservice.domain.exceptions.DishAlreadyExistsException;
import com.pragma.powerup.usermicroservice.domain.exceptions.DishNotBelongRestaurantException;
import com.pragma.powerup.usermicroservice.domain.exceptions.DishNotFoundException;
import com.pragma.powerup.usermicroservice.domain.exceptions.EmployeeAlreadyAssignedException;
import com.pragma.powerup.usermicroservice.domain.exceptions.EmployeeNoOrdersException;
import com.pragma.powerup.usermicroservice.domain.exceptions.NoDataFoundException;
import com.pragma.powerup.usermicroservice.domain.exceptions.OrderAlreadyExistsException;
import com.pragma.powerup.usermicroservice.domain.exceptions.OrderNotAssignEmployeeException;
import com.pragma.powerup.usermicroservice.domain.exceptions.OrderNotBelongCustomerException;
import com.pragma.powerup.usermicroservice.domain.exceptions.OrderNotFoundException;
import com.pragma.powerup.usermicroservice.domain.exceptions.OrderReceivesNoMoreDishesException;
import com.pragma.powerup.usermicroservice.domain.exceptions.OrderCannotBeCanceledException;
import com.pragma.powerup.usermicroservice.domain.exceptions.OrderStatusCannotChangedException;
import com.pragma.powerup.usermicroservice.domain.exceptions.OwnerNotAuthorizedForUpdateException;
import com.pragma.powerup.usermicroservice.domain.exceptions.RestaurantAlreadyExistsException;
import com.pragma.powerup.usermicroservice.domain.exceptions.RestaurantNotFoundException;
import com.pragma.powerup.usermicroservice.domain.exceptions.RestaurantOwnerIdException;
import com.pragma.powerup.usermicroservice.domain.exceptions.RoleNotAllowedForCreationException;
import com.pragma.powerup.usermicroservice.domain.exceptions.StatusInvalidException;
import com.pragma.powerup.usermicroservice.domain.exceptions.UserHasNoEmployeeRoleException;
import com.pragma.powerup.usermicroservice.domain.exceptions.UserNotFoundException;
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
import static com.pragma.powerup.usermicroservice.configuration.utils.Constants.CONFLICT_TWILIO_FEIGN_MESSAGE;
import static com.pragma.powerup.usermicroservice.configuration.utils.Constants.DISH_ALREADY_EXISTS_MESSAGE;
import static com.pragma.powerup.usermicroservice.configuration.utils.Constants.DISH_NOT_BELONG_RESTAURANT_MESSAGE;
import static com.pragma.powerup.usermicroservice.configuration.utils.Constants.DISH_NOT_FOUND_MESSAGE;
import static com.pragma.powerup.usermicroservice.configuration.utils.Constants.EMPLOYEE_ALREADY_ASSIGNED_MESSAGE;
import static com.pragma.powerup.usermicroservice.configuration.utils.Constants.EMPLOYEE_NO_ORDERS_MESSAGE;
import static com.pragma.powerup.usermicroservice.configuration.utils.Constants.NO_DATA_FOUND_MESSAGE;
import static com.pragma.powerup.usermicroservice.configuration.utils.Constants.ORDER_ALREADY_EXISTS_MESSAGE;
import static com.pragma.powerup.usermicroservice.configuration.utils.Constants.ORDER_CANNOT_BE_CANCELED_MESSAGE;
import static com.pragma.powerup.usermicroservice.configuration.utils.Constants.ORDER_NOT_ASSIGN_EMPLOYEE_MESSAGE;
import static com.pragma.powerup.usermicroservice.configuration.utils.Constants.ORDER_NOT_BELONG_CUSTOMER_MESSAGE;
import static com.pragma.powerup.usermicroservice.configuration.utils.Constants.ORDER_NOT_FOUND_MESSAGE;
import static com.pragma.powerup.usermicroservice.configuration.utils.Constants.ORDER_NOT_RECEIVES_DISHES_MESSAGE;
import static com.pragma.powerup.usermicroservice.configuration.utils.Constants.ORDER_STATUS_CANNOT_CHANGE_MESSAGE;
import static com.pragma.powerup.usermicroservice.configuration.utils.Constants.OWNER_NOT_AUTHORIZED_FOR_UPDATE_MESSAGE;
import static com.pragma.powerup.usermicroservice.configuration.utils.Constants.RESPONSE_ERROR_MESSAGE_KEY;
import static com.pragma.powerup.usermicroservice.configuration.utils.Constants.RESTAURANT_ALREADY_EXISTS_MESSAGE;
import static com.pragma.powerup.usermicroservice.configuration.utils.Constants.RESTAURANT_NOT_FOUND_MESSAGE;
import static com.pragma.powerup.usermicroservice.configuration.utils.Constants.RESTAURANT_OWNER_ID_MESSAGE;
import static com.pragma.powerup.usermicroservice.configuration.utils.Constants.ROLE_NOT_ALLOWED_MESSAGE;
import static com.pragma.powerup.usermicroservice.configuration.utils.Constants.STATUS_INVALID_MESSAGE;
import static com.pragma.powerup.usermicroservice.configuration.utils.Constants.USER_HAS_NO_EMPLOYEE_ROLE_MESSAGE;
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
    @ExceptionHandler(OrderAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleOrderAlreadyExistsException(
            OrderAlreadyExistsException orderAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, ORDER_ALREADY_EXISTS_MESSAGE));
    }
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleOrderNotFoundException(
            OrderNotFoundException orderNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, ORDER_NOT_FOUND_MESSAGE));
    }
    @ExceptionHandler(OrderNotBelongCustomerException.class)
    public ResponseEntity<Map<String, String>> handleOrderNotBelongCustomerException(
            OrderNotBelongCustomerException orderNotBelongCustomerException) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, ORDER_NOT_BELONG_CUSTOMER_MESSAGE));
    }
    @ExceptionHandler(OrderReceivesNoMoreDishesException.class)
    public ResponseEntity<Map<String, String>> handleOrderReceivesNoMoreDishesException(
            OrderReceivesNoMoreDishesException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, ORDER_NOT_RECEIVES_DISHES_MESSAGE));
    }
    @ExceptionHandler(DishNotBelongRestaurantException.class)
    public ResponseEntity<Map<String, String>> handleDishNotBelongRestaurantException(
            DishNotBelongRestaurantException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, DISH_NOT_BELONG_RESTAURANT_MESSAGE));
    }
    @ExceptionHandler(EmployeeNoOrdersException.class)
    public ResponseEntity<Map<String, String>> handleEmployeeNoOrdersException(
            EmployeeNoOrdersException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, EMPLOYEE_NO_ORDERS_MESSAGE));
    }
    @ExceptionHandler(OrderNotAssignEmployeeException.class)
    public ResponseEntity<Map<String, String>> handleOrderNotAssignEmployeeException(
            OrderNotAssignEmployeeException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, ORDER_NOT_ASSIGN_EMPLOYEE_MESSAGE));
    }
    @ExceptionHandler(EmployeeAlreadyAssignedException.class)
    public ResponseEntity<Map<String, String>> handleEmployeeAlreadyAssignedException(
            EmployeeAlreadyAssignedException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, EMPLOYEE_ALREADY_ASSIGNED_MESSAGE));
    }
    @ExceptionHandler(UserHasNoEmployeeRoleException.class)
    public ResponseEntity<Map<String, String>> handleUserIdNotEmployeeRoleException(
            UserHasNoEmployeeRoleException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, USER_HAS_NO_EMPLOYEE_ROLE_MESSAGE));
    }
    @ExceptionHandler(ValidationModelException.class)
    public ResponseEntity<Map<String, Map<String, String>>> handleValidationModelException(
            ValidationModelException domainException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, domainException.getException()));
    }
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Map<String, String>> handleFeignException(FeignException feignException) throws InterruptedException {
        int status = HttpStatus.REQUEST_TIMEOUT.value();
        String message = Constants.FEIGN_CLIENT_MESSAGE;
        if (feignException.status() > 100) {
            status = feignException.status();
            message = feignException.contentUTF8();
        }
        Thread.sleep(1000);
        return ResponseEntity.status(status)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, message));
    }
    @ExceptionHandler(OrderStatusCannotChangedException.class)
    public ResponseEntity<Map<String, String>> handleOrderStatusCannotChangeException(
            OrderStatusCannotChangedException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, ORDER_STATUS_CANNOT_CHANGE_MESSAGE));
    }
    @ExceptionHandler(OrderCannotBeCanceledException.class)
    public ResponseEntity<Map<String, String>> handleOrderCannotBeCanceledException(
            OrderCannotBeCanceledException ex) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, ORDER_CANNOT_BE_CANCELED_MESSAGE));
    }
    @ExceptionHandler(StatusInvalidException.class)
    public ResponseEntity<Map<String, String>> handleStatusInvalidException(
            StatusInvalidException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, STATUS_INVALID_MESSAGE));
    }
    @ExceptionHandler(TwilioFeignClientException.class)
    public ResponseEntity<Map<String, String>> handleTwilioFeignClientException(
            TwilioFeignClientException ex) {
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, CONFLICT_TWILIO_FEIGN_MESSAGE));
    }
}
