package com.pragma.powerup.usermicroservice.configuration.utils;

public class Constants {

    private Constants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String OWNER_ROLE_NAME = "OWNER";
    public static final int MAX_PAGE_SIZE = 10;
    public static final String RESPONSE_MESSAGE_KEY = "message";
    public static final String RESPONSE_DATA_KEY = "data";
    public static final String RESPONSE_ERROR_MESSAGE_KEY = "error";
    public static final String WRONG_CREDENTIALS_MESSAGE = "Wrong credentials";
    public static final String USER_NOT_FOUND_MESSAGE = "No user found with the role provided";
    public static final String ROLE_NOT_FOUND_MESSAGE = "No role found with the id provided";
    public static final String ROLE_NOT_ALLOWED_MESSAGE = "Permission to create with that role has not been granted";
    public static final String RESTAURANT_CREATED_MESSAGE = "Restaurant created successfully";
    public static final String RESTAURANT_ALREADY_EXISTS_MESSAGE = "A restaurant already exists with that id";
    public static final String RESTAURANT_NOT_FOUND_MESSAGE = "The restaurant not found with that id";
    public static final String RESTAURANT_OWNER_ID_MESSAGE = "The id does not belong to an owner";
    public static final String DISH_CREATED_MESSAGE = "Dish created successfully";
    public static final String DISH_UPDATED_MESSAGE = "Dish updated successfully";
    public static final String DISH_ALREADY_EXISTS_MESSAGE = "A dish already exists with that id";
    public static final String DISH_NOT_FOUND_MESSAGE = "The dish not found with that id";
    public static final String OWNER_NOT_AUTHORIZED_FOR_UPDATE_MESSAGE = "Owner not authorized to update this dish";
    public static final String NO_DATA_FOUND_MESSAGE = "No data found for the requested petition";
    public static final String CATEGORY_NOT_FOUND_MESSAGE = "The category not found with that id";
    public static final String ACCESS_TOKEN_SECRET = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXvCJ9";
    public static final Long ACCESS_TOKEN_VALIDITY_SECONDS = 3600L;
    public static final String SWAGGER_TITLE_MESSAGE = "User API Pragma Power Up";
    public static final String SWAGGER_DESCRIPTION_MESSAGE = "User microservice";
    public static final String SWAGGER_VERSION_MESSAGE = "1.0.0";
    public static final String SWAGGER_LICENSE_NAME_MESSAGE = "Apache 2.0";
    public static final String SWAGGER_LICENSE_URL_MESSAGE = "http://springdoc.org";
    public static final String SWAGGER_TERMS_OF_SERVICE_MESSAGE = "http://swagger.io/terms/";
}
