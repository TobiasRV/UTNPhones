package com.utn.utnphones.model;


import com.utn.utnphones.model.enums.LineStatus;
import com.utn.utnphones.model.enums.LineType;
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

public class Line {

    @Id
    @GeneratedValue
    private Integer idLine;

    @NotNull
    private User user;

    @NotNull
    private City city;

    @NotNull
    private String phoneNumber;

    @NotNull
    private LineType lineType;

    @NotNull
    private LineStatus lineStatus;

}
