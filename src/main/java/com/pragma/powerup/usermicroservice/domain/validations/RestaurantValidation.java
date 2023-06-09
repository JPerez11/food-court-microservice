package com.pragma.powerup.usermicroservice.domain.validations;

import com.pragma.powerup.usermicroservice.domain.exceptions.ValidationModelException;
import com.pragma.powerup.usermicroservice.domain.models.RestaurantModel;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RestaurantValidation {

    private static Pattern pattern;
    private static Matcher matcher;
    private static Map<String, String> exceptionMap;
    private static final String EMPTY_FIELD = "This field cannot empty";
    private static final String INVALID_FORMAT = "The format is invalid";
    private static final String PHONE_NUMBER_REGEX = "^\\+?\\d{9,12}$";
    private static final String DOCUMENT_NUMBER_REGEX = "^\\d{9,13}$";

    private RestaurantValidation() {}

    /**
     * Method to validate an instance
     * @param restaurantModel Instance restaurantModel
     */
    public static void restaurantValidate(RestaurantModel restaurantModel) {
        exceptionMap = new HashMap<>();
        // Validate data if empty
        validateField(restaurantModel.getName(), "name", EMPTY_FIELD,
                RestaurantValidation::isStringEmpty);
        validateField(restaurantModel.getTaxIdNumber(), "nit", EMPTY_FIELD,
                RestaurantValidation::isStringEmpty);
        validateField(restaurantModel.getAddress(), "address", EMPTY_FIELD,
                RestaurantValidation::isStringEmpty);
        validateField(restaurantModel.getPhoneNumber(), "phone", EMPTY_FIELD,
                RestaurantValidation::isStringEmpty);
        validateField(restaurantModel.getLogoUrl(), "birthdate", EMPTY_FIELD,
                RestaurantValidation::isStringEmpty);
        validateField(String.valueOf(restaurantModel.getIdOwner()), "Owner ID", EMPTY_FIELD,
                RestaurantValidation::isStringEmpty);
        //Validate data if badly formatted
        validateField(restaurantModel.getTaxIdNumber(), "nit format", INVALID_FORMAT,
                RestaurantValidation::isDocumentValid);
        validateField(restaurantModel.getPhoneNumber(), "phone format", INVALID_FORMAT,
                RestaurantValidation::isPhoneValid);

        if (!exceptionMap.isEmpty()) {
            throw new ValidationModelException(exceptionMap);
        }

    }

    private static void validateField(String field, String fieldName,
                                      String errorMessage, Predicate<String> validator) {
        if(validator.test(field)) {
            exceptionMap.put(fieldName, String.format(errorMessage));
        }
    }

    /**
     * Method to validate strings
     * @param data The string cannot empty
     * @return true if string is empty
     */
    private static boolean isStringEmpty(String data) {
        return data.trim().isEmpty();
    }

    /**
     * Method to validate document
     * @param document The document must be formatted
     * @return true if format is invalid
     */
    private static boolean isDocumentValid(String document) {
        pattern = Pattern
                .compile(DOCUMENT_NUMBER_REGEX);
        matcher = pattern.matcher(document);
        return !matcher.find();
    }

    /**
     * Method to validate phone
     * @param phone The phone must be formatted and not exceed the size
     * @return true if format is invalid
     */
    private static boolean isPhoneValid(String phone) {
        pattern = Pattern
                .compile(PHONE_NUMBER_REGEX);
        matcher = pattern.matcher(phone);
        return !matcher.find();
    }

}
