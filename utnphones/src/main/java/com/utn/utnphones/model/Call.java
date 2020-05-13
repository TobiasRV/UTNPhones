package com.utn.utnphones.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class Call {

    @Id
    @GeneratedValue
    private Integer idCall;

    @NotNull
    private Line originLine;

    @NotNull
    private Line destinationLine;

    @NotNull
    private Timestamp callDate;

    @NotNull
    private Rate rate;

    @NotNull
    private Integer callDuration;

    @NotNull
    private Double callCost;

    @NotNull
    private Double callPrice;


}
