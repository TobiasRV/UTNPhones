package com.utn.utnphones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddCallDto {
    private String originNumber;
    private String destinationNumber;
    private Timestamp date;
    private Integer duration;
}