package com.utn.utnphones.dto;

import com.utn.utnphones.model.enums.LineStatus;
import com.utn.utnphones.model.enums.LineType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateLineDto {
    private Integer cityId;
    private String phoneNumber;
    private LineType lineType;
    private LineStatus lineStatus;
}
