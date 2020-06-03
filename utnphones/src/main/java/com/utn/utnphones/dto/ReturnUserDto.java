package com.utn.utnphones.dto;

import com.utn.utnphones.model.City;
import com.utn.utnphones.model.Line;
import com.utn.utnphones.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReturnUserDto {

    private Integer id;
    private String username;
    private String name;
    private String lastname;
    private Integer dni;
    private City city;
    private Enum status;
    private List<Line> lineList;
}
