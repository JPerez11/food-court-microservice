package com.pragma.powerup.usermicroservice.domain.model;

public class TwilioModel {

    private String message;
    private String phoneNumber;

    public TwilioModel() {}

    public TwilioModel(String message, String phoneNumber) {
        this.message = message;
        this.phoneNumber = phoneNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
