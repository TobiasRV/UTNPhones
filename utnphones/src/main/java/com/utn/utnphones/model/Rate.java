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

public class Rate {

    @Id
    @GeneratedValue
    private Integer idRate;

    @NotNull
    private City originCity;

    @NotNull
    private City destinationCity;

    @NotNull
    private Double costPerMinute;

}
