package com.utn.utnphones.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "calls")

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Call {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_call")
    private Integer idCall;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_origin_line", nullable = false)
    private Line originLine;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_destination_line", nullable = false)
    private Line destinationLine;

    @CreationTimestamp
    private Timestamp callDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rate")
    private Rate rate;

    @Column(name = "call_duration", nullable = false)
    private Integer callDuration;

    @Column(name = "call_cost")
    private Double callCost;

    @Column(name = "call_price")
    private Double callPrice;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_bill")
    private Bill bill;

}
