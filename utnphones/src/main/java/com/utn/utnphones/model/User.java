package com.utn.utnphones.model;

import com.utn.utnphones.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class User {

    @Id
    @GeneratedValue
    private Integer idUser;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    private String lastname;

    @NotNull
    private Integer dni;

    @NotNull
    private City city;

    @NotNull
    private UserRole role;


}
