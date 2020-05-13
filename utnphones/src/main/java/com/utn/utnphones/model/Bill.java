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

public class Bill {

    @Id
    @GeneratedValue
    private Integer idBill;

    @NotNull
    private Line line;

    @NotNull
    private Double totalProductionCost;

    @NotNull
    private Double totalPrice;

    @NotNull
    private Timestamp issueDate;

    @NotNull
    private Timestamp expirationDate;

    @NotNull
    private Boolean paid;
}
