package com.utn.utnphones.controller;


import com.utn.utnphones.exceptions.CityNotFoundException;
import com.utn.utnphones.model.City;
import com.utn.utnphones.service.CityService;
import com.utn.utnphones.service.ProvinceService;
import org.junit.After;
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

    CityController controller;

    @Mock
    CityService cityService;
    @Mock
    ProvinceService provinceService;


    @Before
    public void setUp() throws Exception {
        initMocks(this);
        controller = new CityController(cityService, provinceService);
    }

    @Test
    public void getAll() {
        City c1 = new City(1, "Miramar", null, "2291");
        City c2 = new City(1, "Mar del Plata", null, "223");


        List<City> expected = new ArrayList<>();
        expected.add(c1);
        expected.add(c2);

        Mockito.when(cityService.getAll()).thenReturn(expected);

        ResponseEntity<List<City>> returned = controller.getAll();

        assertNotNull(returned);
        assertThat(returned.getBody().size(), is(2));
        assertEquals(returned.getBody(), expected);
    }

    @Test
    public void getAllEmpty() {
        List<City> expected = new ArrayList<>();

        when(cityService.getAll()).thenReturn(expected);

        ResponseEntity<List<City>> returned = controller.getAll();

        assertNotNull(returned);
        assertEquals(HttpStatus.NO_CONTENT, returned.getStatusCode());
    }

    @Test
    public void getCityById() throws CityNotFoundException {
        City expected = new City(1, "Miramar", null, "2291");
        Mockito.when(cityService.getCityById(1)).thenReturn(expected);

        ResponseEntity returned = controller.getCityById(1);
        assertNotNull(returned);
        assertEquals(expected, returned.getBody());
    }



    @After
    public void tearDown() throws Exception {
    }
}