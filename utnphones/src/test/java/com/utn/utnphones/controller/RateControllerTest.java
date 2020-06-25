package com.utn.utnphones.controller;

import com.utn.utnphones.dto.UpdateRateDto;
import com.utn.utnphones.exceptions.CityNotFoundException;
import com.utn.utnphones.exceptions.RateNotFoundException;
import com.utn.utnphones.model.Rate;
import com.utn.utnphones.service.CityService;
import com.utn.utnphones.service.RateService;
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

public class RateControllerTest {

    RateController controller;

    @Mock
    RateService rateService;
    @Mock
    CityService cityService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        controller = new RateController(rateService, cityService);
    }


    @Test
    public void getAll() {
        Rate r1 = new Rate(1, null, null, 2.00, 2.00);
        Rate r2 = new Rate(2, null, null, 2.00, 2.00);

        List<Rate> expected = new ArrayList<>();
        expected.add(r1);
        expected.add(r2);

        when(rateService.getAll()).thenReturn(expected);

        ResponseEntity<List<Rate>> returned = controller.getAll();

        assertNotNull(returned);
        assertThat(returned.getBody().size(), is(2));
        assertEquals(expected, returned.getBody());

    }

    @Test
    public void getAllEmpty() {

        List<Rate> expected = new ArrayList<>();

        when(rateService.getAll()).thenReturn(expected);

        ResponseEntity<List<Rate>> returned = controller.getAll();

        assertNotNull(returned);
        assertEquals(HttpStatus.NO_CONTENT, returned.getStatusCode());

    }

    @Test
    public void addRate(){

    }

    @Test
    public void getRateByCities() throws CityNotFoundException {
        Rate r1 = new Rate(1, null, null, 2.00, 2.00);
        List<Rate> expected = new ArrayList<>();
        expected.add(r1);

        Mockito.when(rateService.getRateByCities(1, 2)).thenReturn(expected);
        Mockito.when(cityService.existsById(2)).thenReturn(true);
        Mockito.when(cityService.existsById(1)).thenReturn(true);

        ResponseEntity<List<Rate>> returned = controller.getRateByCities(1, 2);

        assertNotNull(returned);
        assertThat(returned.getBody().size(), is(1));
        assertEquals(expected,returned.getBody());

    }

    @Test(expected = CityNotFoundException.class)
    public void getRateByCitiesFromCityDoesNotExcist() throws CityNotFoundException {
        Mockito.when(cityService.existsById(1)).thenReturn(false);
        controller.getRateByCities(1,2);
    }

    @Test(expected = CityNotFoundException.class)
    public void getRateByCitiesToCityDoesNotExcist() throws CityNotFoundException {
        Mockito.when(cityService.existsById(1)).thenReturn(true);
        Mockito.when(cityService.existsById(2)).thenReturn(false);
        controller.getRateByCities(1,2);
    }

    @Test
    public void getRateByCitiesToCityNull() throws CityNotFoundException {
        Rate r1 = new Rate(1, null, null, 2.00, 2.00);
        Rate r2 = new Rate(2, null, null, 2.00, 2.00);

        List<Rate> expected = new ArrayList<>();
        expected.add(r1);
        expected.add(r2);

        Mockito.when(cityService.existsById(1)).thenReturn(true);
        Mockito.when(rateService.getRateByCities(1,null)).thenReturn(expected);
        ResponseEntity<List<Rate>> returned = controller.getRateByCities(1, null);

        assertNotNull(returned);
        assertThat(returned.getBody().size(), is(2));
        assertEquals(expected, returned.getBody());
    }

    @Test
    public void getRateByCitiesEmpty() throws CityNotFoundException {

        List<Rate> expected = new ArrayList<>();

        Mockito.when(rateService.getRateByCities(1, 2)).thenReturn(expected);
        Mockito.when(cityService.existsById(2)).thenReturn(true);
        Mockito.when(cityService.existsById(1)).thenReturn(true);

        ResponseEntity<List<Rate>> returned = controller.getRateByCities(1, 2);

        assertNotNull(returned);
        assertEquals(HttpStatus.NO_CONTENT, returned.getStatusCode());

    }

    @Test(expected = CityNotFoundException.class)
    public void getRateByCitiesError() throws CityNotFoundException {
        Mockito.when(cityService.getCityById(5)).thenThrow(new CityNotFoundException());
        cityService.getCityById(5);
    }

    @Test
    public void updateRate() throws RateNotFoundException, CityNotFoundException {
        UpdateRateDto updateRateDto = new UpdateRateDto(1.00,2.00);

        Mockito.when(cityService.existsById(1)).thenReturn(true);
        Mockito.when(cityService.existsById(2)).thenReturn(true);

        ResponseEntity returned = controller.updateRate(1,2,updateRateDto);

        assertNotNull(returned);
        assertEquals(HttpStatus.OK,returned.getStatusCode());
    }

    @Test(expected = CityNotFoundException.class)
    public void updateRateError() throws RateNotFoundException, CityNotFoundException{
        UpdateRateDto updateRateDto = new UpdateRateDto(1.00,2.00);

        Mockito.when(cityService.existsById(1)).thenReturn(false);
        Mockito.when(cityService.existsById(2)).thenReturn(true);

        controller.updateRate(1,2,updateRateDto);

    }

    @Test(expected = CityNotFoundException.class)
    public void updateRateToCityDoentExist() throws RateNotFoundException, CityNotFoundException {
        UpdateRateDto updateRateDto = new UpdateRateDto(1.00,2.00);

        Mockito.when(cityService.existsById(1)).thenReturn(true);
        Mockito.when(cityService.existsById(2)).thenReturn(false);

        ResponseEntity returned = controller.updateRate(1,2,updateRateDto);
    }

}