package com.utn.utnphones.model;

import com.utn.utnphones.model.enums.BillStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "bills")

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bill")
    private Integer idBill;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_line")
    private Line line;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user")
    private User user;

    @Column(name = "qty_calls")
    private Integer qtyCalls;

    @Column(name = "total_production_cost")
    private Double totalProductionCost;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "issue_date")
    private Timestamp issueDate;

    @Column(name = "expiration_date")
    private Timestamp expirationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BillStatus status;
}
