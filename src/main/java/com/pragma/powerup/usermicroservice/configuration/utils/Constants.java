package com.pragma.powerup.usermicroservice.configuration.utils;

public class Constants {


    private Constants() {
        throw new IllegalStateException("Utility class");
    }


    public static final String ADMIN_ROLE_NAME = "ADMIN";
    public static final String OWNER_ROLE_NAME = "OWNER";
    public static final String EMPLOYEE_ROLE_NAME = "EMPLOYEE";
    public static final String CUSTOMER_ROLE_NAME = "CUSTOMER";
    public static final String RESPONSE_MESSAGE_KEY = "message";
    public static final String RESPONSE_DATA_KEY = "data";
    public static final String RESPONSE_ERROR_MESSAGE_KEY = "error";
    public static final String WRONG_CREDENTIALS_MESSAGE = "Wrong credentials";
    public static final String USER_NOT_FOUND_MESSAGE = "No user found with the role provided";
    public static final String ROLE_NOT_ALLOWED_MESSAGE = "Permission to create with that role has not been granted";
    public static final String RESTAURANT_CREATED_MESSAGE = "Restaurant created successfully";
    public static final String RESTAURANT_ALREADY_EXISTS_MESSAGE = "A restaurant already exists with that id";
    public static final String RESTAURANT_NOT_FOUND_MESSAGE = "Restaurant not found";
    public static final String RESTAURANT_OWNER_ID_MESSAGE = "The id does not belong to an owner";
    public static final String DISH_CREATED_MESSAGE = "Dish created successfully";
    public static final String DISH_UPDATED_MESSAGE = "Dish updated successfully";
    public static final String DISH_ALREADY_EXISTS_MESSAGE = "A dish already exists with that id";
    public static final String DISH_NOT_FOUND_MESSAGE = "Dish not found";
    public static final String OWNER_NOT_AUTHORIZED_FOR_UPDATE_MESSAGE = "Owner not authorized to update this dish";
    public static final String NO_DATA_FOUND_MESSAGE = "No data found for the requested petition";
    public static final String CATEGORY_NOT_FOUND_MESSAGE = "Category not found";
    public static final String ORDER_CREATED_MESSAGE = "Order created successfully";
    public static final String ASSIGN_EMPLOYEE_MESSAGE = "Employee assignment successfully";
    public static final String STATUS_UPDATED_MESSAGE = "Order status updated successfully";
    public static final String ORDER_DETAIL_CREATED_MESSAGE = "Order detail created successfully";
    public static final String ORDER_ALREADY_EXISTS_MESSAGE = "Order already exists";
    public static final String ORDER_NOT_FOUND_MESSAGE = "Order not found";
    public static final String ORDER_NOT_BELONG_CUSTOMER_MESSAGE = "Order does not belong to the customer";
    public static final String ORDER_NOT_ASSIGN_EMPLOYEE_MESSAGE = "The order is not assigned to the employee";
    public static final String ORDER_NOT_RECEIVES_DISHES_MESSAGE = "Order receives no more dishes";
    public static final String DISH_NOT_BELONG_RESTAURANT_MESSAGE = "The dish does not belong to the restaurant in which it is being ordered";
    public static final String EMPLOYEE_NO_ORDERS_MESSAGE = "Employee has no assigned orders";
    public static final String EMPLOYEE_ALREADY_ASSIGNED_MESSAGE = "The employee is already assigned to a restaurant";
    public static final String USER_HAS_NO_EMPLOYEE_ROLE_MESSAGE = "User has no employee role";
    public static final String ORDER_STATUS_CANNOT_CHANGE_MESSAGE = "Order status cannot be changed";
    public static final String STATUS_INVALID_MESSAGE = "The status provided is invalid";
    public static final String CONFLICT_TWILIO_FEIGN_MESSAGE = "An error occurred with the Twilio service";
    public static final String PENDING_STATUS = "PENDING";
    public static final String CANCELED_STATUS = "CANCELED";
    public static final String PREPARING_STATUS = "PREPARING";
    public static final String READY_STATUS = "READY";
    public static final String DELIVERED_STATUS = "DELIVERED";
    public static final String NOTIFICATION_MESSAGE = "The order is now ready!";
    public static final String ACCESS_TOKEN_SECRET = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXvCJ9";
    public static final String SWAGGER_TITLE_MESSAGE = "User API Pragma Power Up";
    public static final String SWAGGER_DESCRIPTION_MESSAGE = "User microservice";
    public static final String SWAGGER_VERSION_MESSAGE = "1.0.0";
    public static final String SWAGGER_LICENSE_NAME_MESSAGE = "Apache 2.0";
    public static final String SWAGGER_LICENSE_URL_MESSAGE = "http://springdoc.org";
    public static final String SWAGGER_TERMS_OF_SERVICE_MESSAGE = "http://swagger.io/terms/";
}
