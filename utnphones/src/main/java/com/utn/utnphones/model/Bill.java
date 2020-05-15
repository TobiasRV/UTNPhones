package com.utn.utnphones.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "bills")

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bill")
    private Integer idBill;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_line")
    private Line line;

    @Column(name = "total_production_cost")
    private Double totalProductionCost;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "issue_date")
    private Timestamp issueDate;

    @Column(name = "expiration_date")
    private Timestamp expirationDate;

    @NotNull
    private Boolean paid;
}
