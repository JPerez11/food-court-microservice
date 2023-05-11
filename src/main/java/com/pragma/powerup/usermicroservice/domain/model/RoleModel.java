package com.pragma.powerup.usermicroservice.domain.model;

/**
 * @author Author Jhoan Perez
 * Role POJO class for business logic
 */
public class RoleModel {

    /**
     * Role class attributes
     */
    private Long id;
    private String name;
    private String description;

    /**
     * Empty constructor
     */
    public RoleModel() {
    }

    /**
     * Constructor with all parameters
     * @param id is the identifier
     * @param name is the name
     * @param description is the description
     */
    public RoleModel(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
