package com.utn.utnphones.dto;

import com.utn.utnphones.model.City;
import com.utn.utnphones.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UpdateUserDto {

    private String username;
    private String password;
    private String name;
    private String lastname;
    private Integer dni;
    private City city;
    private UserRole role;

}
