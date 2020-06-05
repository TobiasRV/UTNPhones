package com.utn.utnphones.controller;

import com.utn.utnphones.exceptions.CityNotFoundException;
import com.utn.utnphones.model.Rate;
import com.utn.utnphones.service.RateService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class RateControllerTest {

    RateController controller;

    @Mock
    RateService rateService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        controller = new RateController(rateService);
    }

    @Test
    public void addRate() {
    }

    @Test
    public void getRateByCities() throws CityNotFoundException {
        Rate r1 = new Rate(1,null,null,2.00,2.00);
        List<Rate> expected = new ArrayList<>();
        expected.add(r1);

        Mockito.when(rateService.getRateByCities(1,2)).thenReturn(expected);

        List<Rate> returned = rateService.getRateByCities(1,2);

        if (returned != null) {
            assertThat(returned.size(), is(1));
        }

        assertEquals(returned, expected);

    }
}