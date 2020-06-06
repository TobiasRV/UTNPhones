package com.utn.utnphones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCityDto {
    private String cityName;
    private Integer idProvince;
    private String prefix;
}