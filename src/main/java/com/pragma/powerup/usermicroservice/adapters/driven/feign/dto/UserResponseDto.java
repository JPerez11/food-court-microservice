package com.pragma.powerup.usermicroservice.adapters.driven.feign.dto;


import com.pragma.powerup.usermicroservice.domain.model.RoleModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private String firstName;
    private String lastName;
    private String documentNumber;
    private String phoneNumber;
    private LocalDate birthdate;
    private String email;
    private RoleModel role;

}
