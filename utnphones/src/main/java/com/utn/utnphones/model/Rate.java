package com.utn.utnphones.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "rates")

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rate")
    private Integer idRate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_origin_city", nullable = false)
    private City originCity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_destination_city", nullable = false)
    private City destinationCity;

    @Column(name = "cost_per_minute", nullable = false)
    private Double costPerMinute;

    @Column(name = "price_per_minute", nullable = false)
    private Double pricePerMinute;

}
