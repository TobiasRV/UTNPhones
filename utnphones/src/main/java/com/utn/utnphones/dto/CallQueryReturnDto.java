package com.utn.utnphones.dto;

import com.utn.utnphones.model.Line;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class CallQueryReturnDto {
    private Integer idCall;
    private Line originLine;
    private Line destinationLine;
    private Timestamp callDate;
    private Integer idRate;
    private Integer callDuration;
    private Double callCost;
    private Double callPrice;
    private Integer idBill;
}
