package com.utn.utnphones.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.utn.utnphones.model.Line;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LineAndQtyOfCallsDto {

    @JsonProperty
    String phone_number;

    @JsonProperty
    Integer qtyOfCalls;


}
