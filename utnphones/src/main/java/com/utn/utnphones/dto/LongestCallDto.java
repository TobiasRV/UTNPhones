package com.utn.utnphones.dto;

import com.utn.utnphones.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder

public class LongestCallDto {

    private User user;
    private Integer callDuration;
}
