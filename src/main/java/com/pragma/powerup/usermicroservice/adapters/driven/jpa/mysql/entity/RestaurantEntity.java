package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "RESTAURANTS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true, name = "tax_identification_number")
    private String taxIdNumber;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false, length = 13, name = "phone_number")
    private String phoneNumber;
    @Column(nullable = false, name = "logo_url")
    private String logoUrl;
    @Column(nullable = false, name = "id_owner")
    private Long idOwner;
}
