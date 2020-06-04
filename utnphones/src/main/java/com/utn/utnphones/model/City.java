package com.utn.utnphones.model;


import com.fasterxml.jackson.annotation.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "cities")

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_city")
    private Integer idCity;

    @Column(name = "city_name", nullable = false, unique = true)
    private String cityName;

    @JoinColumn(name = "id_province", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Province province;

    @Column(nullable = false, unique = true, length = 5)
    private String prefix;

}
