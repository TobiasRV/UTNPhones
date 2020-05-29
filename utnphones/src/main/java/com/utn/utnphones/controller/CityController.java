package com.utn.utnphones.controller;

import com.utn.utnphones.model.City;
import com.utn.utnphones.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/city")
public class CityController {

    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/")
    public List<City> getAll() {
        return cityService.getAll();
    }

    @PostMapping("/")
    public void addCity(@RequestBody City c) {
        cityService.addCity(c);
    }
}
