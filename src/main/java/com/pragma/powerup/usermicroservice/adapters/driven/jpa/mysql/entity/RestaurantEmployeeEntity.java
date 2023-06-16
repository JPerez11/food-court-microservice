package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "RESTAURANT_EMPLOYEE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantEmployeeEntity {

    @EmbeddedId
    private RestaurantEmployeeId restaurantEmployeeId;

    @Column(name = "employee_id", nullable = false, insertable = false, updatable = false)
    private Long employeeId;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false, insertable = false, updatable = false)
    private RestaurantEntity restaurantEntity;

    private double salary;
    private LocalDate contract;

    @Getter
    @Setter
    @Embeddable
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RestaurantEmployeeId implements Serializable {

        @Column(name = "employee_id")
        private Long employeeId;

        @Column(name = "restaurant_id")
        private Long restaurantId;

    }
}
