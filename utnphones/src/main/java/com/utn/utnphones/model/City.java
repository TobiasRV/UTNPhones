package com.utn.utnphones.model;


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

public class City {

    @Id
    @GeneratedValue
    private Integer idCity;

    @NotNull
    private String cityName;

    @NotNull
    private Province province;

    @NotNull
    private String prefix;



}
