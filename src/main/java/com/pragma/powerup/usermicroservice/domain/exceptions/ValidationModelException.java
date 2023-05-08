package com.pragma.powerup.usermicroservice.domain.exceptions;

import java.util.Map;

public class ValidationModelException extends RuntimeException {

    private final Map<String, String> exception;
    public ValidationModelException(Map<String, String> exception) {
        this.exception = exception;
    }

    public Map<String, String> getException() {
        return exception;
    }
}
