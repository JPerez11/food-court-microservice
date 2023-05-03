package com.pragma.powerup.usermicroservice.domain.model;

public class RestaurantModel {

    private Long id;
    private String name;
    private String taxIdNumber;
    private String address;
    private String phoneNumber;
    private String logoUrl;
    private Long idOwner;

    public RestaurantModel() {}

    public RestaurantModel(Long id, String name, String taxIdNumber, String address,
                           String phoneNumber, String logoUrl, Long idOwner) {
        this.id = id;
        this.name = name;
        this.taxIdNumber = taxIdNumber;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.logoUrl = logoUrl;
        this.idOwner = idOwner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaxIdNumber() {
        return taxIdNumber;
    }

    public void setTaxIdNumber(String taxIdNumber) {
        this.taxIdNumber = taxIdNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Long getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(Long idOwner) {
        this.idOwner = idOwner;
    }
}