package com.pragma.powerup.usermicroservice.domain.model;

import java.time.LocalDate;

/**
 * @author Jhoan Perez
 * User POJO class for business logic
 */
public class UserModel {

    /**
     * User class attributes
     */
    private Long id;
    private String firstName;
    private String lastName;
    private String documentNumber;
    private String phoneNumber;
    private LocalDate birthdate;
    private String email;
    private String password;
    private RoleModel roleModel;

    /**
     * Empty constructor
     */
    public UserModel() {
    }

    /**
     * Constructor with all parameters
     * @param id is the identifier
     * @param firstName is the first name
     * @param lastName is the surname
     * @param documentNumber is the document number
     * @param phoneNumber is the phone number
     * @param birthdate is the birthdate
     * @param email is the email
     * @param password is the password
     * @param roleModel is the role
     */
    public UserModel(Long id, String firstName, String lastName, String documentNumber, String phoneNumber,
                     LocalDate birthdate, String email, String password, RoleModel roleModel) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.documentNumber = documentNumber;
        this.phoneNumber = phoneNumber;
        this.birthdate = birthdate;
        this.email = email;
        this.password = password;
        this.roleModel = roleModel;
    }

    /**
     * getter and setter methods of the class
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleModel getRoleModel() {
        return roleModel;
    }

    public void setRoleModel(RoleModel roleModel) {
        this.roleModel = roleModel;
    }
}
