package com.utn.utnphones.controller;


import com.utn.utnphones.model.City;
import com.utn.utnphones.service.CityService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class CityControllerTest {

    CityController controller;

    @Mock
    CityService cityService;


    @Before
    public void setUp() throws Exception {
        initMocks(this);
        controller = new CityController(cityService);
    }

    @Test
    public void getAll() {
        City c1 = new City(1,"Miramar", null,"2291");
        City c2 = new City(1,"Mar del Plata", null,"223");


        List<City> expected = new ArrayList<>();
        expected.add(c1);
        expected.add(c2);

        Mockito.when(cityService.getAll()).thenReturn(expected);

        List<City> returned = controller.getAll();


        if (returned != null) {
            assertThat(returned.size(), is(2));
        }


        assertEquals(returned, expected);
    }

    // TODO Hacer test
    @Test
    public void addCity() {


    }
}