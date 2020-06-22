package com.utn.utnphones.controller;

import com.utn.utnphones.dto.CallQueryReturnDto;
import com.utn.utnphones.exceptions.CityNotFoundException;
import com.utn.utnphones.model.Bill;
import com.utn.utnphones.model.Rate;
import com.utn.utnphones.model.enums.BillStatus;
import com.utn.utnphones.service.CityService;
import com.utn.utnphones.service.RateService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

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

        List<Rate> returned = rateService.getAll();

        assertNotNull(returned);
        assertThat(returned.size(), is(2));
        assertEquals(returned, expected);

    }

    @Test
    public void getAllEmpty() {
        HttpStatus response = null;
        List<Rate> expected = new ArrayList<>();

        when(rateService.getAll()).thenReturn(expected);

        List<Rate> returned = rateService.getAll();

        if (returned.size() == 0) {
            response = HttpStatus.NO_CONTENT;
        }
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response);
    }

    @Test
    public void getRateByCities() throws CityNotFoundException {
        Rate r1 = new Rate(1, null, null, 2.00, 2.00);
        List<Rate> expected = new ArrayList<>();
        expected.add(r1);

        Mockito.when(rateService.getRateByCities(1, 2)).thenReturn(expected);

        List<Rate> returned = rateService.getRateByCities(1, 2);

        assertNotNull(returned);
        assertThat(returned.size(), is(1));
        assertEquals(returned, expected);

    }

    @Test
    public void getRateByCitiesEmpty() throws CityNotFoundException {
        HttpStatus response = null;

        List<Rate> expected = new ArrayList<>();

        Mockito.when(rateService.getRateByCities(1, 2)).thenReturn(expected);

        List<Rate> returned = rateService.getRateByCities(1, 2);

        if (returned.size() == 0) {
            response = HttpStatus.NO_CONTENT;
        }
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response);

    }

    @Test(expected = CityNotFoundException.class)
    public void getRateByCitiesError() throws CityNotFoundException {
        Mockito.when(cityService.getCityById(5)).thenThrow(new CityNotFoundException());
        cityService.getCityById(5);
    }

    @After
    public void tearDown() throws Exception {
    }
}