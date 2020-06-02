package com.utn.utnphones.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "provinces")

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class Province {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_province")
    private Integer idProvince;

    @Column(name = "province_name", nullable = false, length = 50)
    private String provinceName;

    @OneToMany(mappedBy = "province")
    @JsonBackReference
    private List<City> cities;
}
