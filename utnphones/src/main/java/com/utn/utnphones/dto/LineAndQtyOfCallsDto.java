package com.utn.utnphones.dto;

import com.utn.utnphones.model.Line;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LineAndQtyOfCallsDto {


    Line line;

    Integer qtyOfCalls;

}
