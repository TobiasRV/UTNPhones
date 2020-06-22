package com.utn.utnphones.service;

import com.utn.utnphones.dto.UpdateCityDto;
import com.utn.utnphones.exceptions.CityNotFoundException;
import com.utn.utnphones.model.City;
import com.utn.utnphones.model.Province;
import com.utn.utnphones.repository.CityRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.parameters.P;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.MockitoAnnotations.initMocks;

public class CityServiceTest {

    CityService cityService;

    @Mock
    CityRepository cityRepository;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        cityService = new CityService(cityRepository);
    }

    @Test
    public void getAll() {
        City c1 = new City(1,"Miramar",null,"2291");
        City c2 = new City(2,"Mar del Plata",null,"223");

        List<City> expected = new ArrayList<>();
        expected.add(c1);
        expected.add(c2);
        Mockito.when(cityRepository.findAll()).thenReturn(expected);

        List<City> returned = cityService.getAll();

        assertThat(returned.size(), is(2));
        assertEquals(expected,returned);
    }

    @Test
    public void addCity() {
        City c1 = new City(1,"Miramar",null,"2291");
        cityService.addCity(c1);
    }

    @Test
    public void existsByIdTrue() {
        Mockito.when(cityRepository.existsById(1)).thenReturn(true);
        boolean returned = cityService.existsById(1);
        assertEquals(true,returned);
    }


    @Test
    public void getCityById() throws CityNotFoundException {
        City expected = new City(1,"Miramar",null,"2291");
        Mockito.when(cityRepository.findById(1)).thenReturn(java.util.Optional.of(expected));
        City returned = cityService.getCityById(1);

        assertEquals(expected,returned);
    }

    @Test(expected = CityNotFoundException.class)
    public void getCityByIdError() throws CityNotFoundException {
        Mockito.when(cityRepository.findById(1).orElseThrow(CityNotFoundException::new)).thenThrow(new CityNotFoundException());
        cityService.getCityById(1);
    }


    // TODO Terminar metodo
    @Test(expected = CityNotFoundException.class)
    public void UpdateCity() throws CityNotFoundException {
        UpdateCityDto updateCityDto = new UpdateCityDto("Miramar",1,"2291");
        Province province = new Province(1,null,null);
        cityService.updateCity(1,updateCityDto, province);
    }

    @After
    public void tearDown() throws Exception {
    }
}