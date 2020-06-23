package com.utn.utnphones.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "provinces")

@NoArgsConstructor
@AllArgsConstructor
@Data

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
