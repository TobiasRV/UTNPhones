package com.utn.utnphones.controller;

import com.utn.utnphones.dto.UpdateCityDto;
import com.utn.utnphones.exceptions.CityNotFoundException;
import com.utn.utnphones.exceptions.ProvinceNotFoundException;
import com.utn.utnphones.model.City;
import com.utn.utnphones.model.Province;
import com.utn.utnphones.service.CityService;
import com.utn.utnphones.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/city")
public class CityController {

    private final CityService cityService;
    private final ProvinceService provinceService;

    @Autowired
    public CityController(CityService cityService, ProvinceService provinceService) {
        this.cityService = cityService;
        this.provinceService = provinceService;
    }

    @GetMapping("/")
    public ResponseEntity<List<City>> getAll() {
        return ResponseEntity.ok(cityService.getAll());
    }

    @GetMapping("/{cityId}")
    public ResponseEntity<City> getCityById(@PathVariable Integer cityId) throws CityNotFoundException {
        return ResponseEntity.ok(cityService.getCityById(cityId));
    }

    @PostMapping("/")
    public ResponseEntity addCity(@RequestBody @Valid City c) {
        City newCity = cityService.addCity(c);
        return ResponseEntity.created(getLocation(newCity)).build();
    }

    @PutMapping("/{cityId}")
    public ResponseEntity updateCity(@PathVariable Integer cityId, @RequestBody UpdateCityDto updateCityDtoCity) throws ProvinceNotFoundException, CityNotFoundException {
        if (updateCityDtoCity.getIdProvince() != null) {
            Province province = provinceService.getProvinceById(updateCityDtoCity.getIdProvince());
            cityService.updateCity(cityId, updateCityDtoCity, province);
        }else
            cityService.updateCity(cityId, updateCityDtoCity,null);

        return ResponseEntity.ok().build();
    }

    private URI getLocation(City city) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{cityId}")
                .buildAndExpand(city.getIdCity())
                .toUri();
    }
}
