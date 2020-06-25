package com.utn.utnphones.controller;


import com.utn.utnphones.dto.UpdateCityDto;
import com.utn.utnphones.exceptions.CityNotFoundException;
import com.utn.utnphones.exceptions.ProvinceNotFoundException;
import com.utn.utnphones.model.City;
import com.utn.utnphones.model.Province;
import com.utn.utnphones.service.CityService;
import com.utn.utnphones.service.ProvinceService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CityControllerTest {

    CityController cityController;

    @Mock
    CityService cityService;
    @Mock
    ProvinceService provinceService;


    @Before
    public void setUp() throws Exception {
        initMocks(this);
        cityController = new CityController(cityService, provinceService);
    }

    @Test
    public void getAll() {
        City c1 = new City(1, "Miramar", null, "2291");
        City c2 = new City(1, "Mar del Plata", null, "223");


        List<City> expected = new ArrayList<>();
        expected.add(c1);
        expected.add(c2);

        Mockito.when(cityService.getAll()).thenReturn(expected);

        ResponseEntity<List<City>> returned = cityController.getAll();

        assertNotNull(returned);
        assertThat(returned.getBody().size(), is(2));
        assertEquals(returned.getBody(), expected);
    }

    @Test
    public void getAllEmpty() {
        List<City> expected = new ArrayList<>();

        when(cityService.getAll()).thenReturn(expected);

        ResponseEntity<List<City>> returned = cityController.getAll();

        assertNotNull(returned);
        assertEquals(HttpStatus.NO_CONTENT, returned.getStatusCode());
    }

    @Test
    public void getCityById() throws CityNotFoundException {
        City expected = new City(1, "Miramar", null, "2291");
        Mockito.when(cityService.getCityById(1)).thenReturn(expected);

        ResponseEntity returned = cityController.getCityById(1);
        assertNotNull(returned);
        assertEquals(expected, returned.getBody());
    }

    @Test
    public void updateCity() throws ProvinceNotFoundException, CityNotFoundException {
        UpdateCityDto updateCityDto = new UpdateCityDto("Miramar", 1, "2291");
        Province p1 = new Province(1, null, null);
        Mockito.when(provinceService.getProvinceById(1)).thenReturn(p1);
        ResponseEntity returned = cityController.updateCity(1, updateCityDto);
        assertEquals(HttpStatus.OK, returned.getStatusCode());
    }

    @Test
    public void updateCityProvinceNull() throws ProvinceNotFoundException, CityNotFoundException {
        UpdateCityDto updateCityDto = new UpdateCityDto("Miramar", null, "2291");
        ResponseEntity returned = cityController.updateCity(1, updateCityDto);
        assertEquals(HttpStatus.OK, returned.getStatusCode());
    }



}