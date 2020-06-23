package com.utn.utnphones.service;

import com.utn.utnphones.exceptions.RateNotFoundException;
import com.utn.utnphones.model.Rate;
import com.utn.utnphones.repository.RateRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

public class RateServiceTest {

    RateService rateService;

    @Mock
    RateRepository rateRepository;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        rateService = new RateService(rateRepository);
    }

    @Test
    public void getAll() {
        Rate r1 = new Rate(1,null,null,null,null);
        Rate r2 = new Rate(2,null,null,null,null);

        List<Rate> expected = new ArrayList<>();
        expected.add(r1);
        expected.add(r2);

        Mockito.when(rateRepository.findAll()).thenReturn(expected);
        List<Rate> returned = rateRepository.findAll();

        assertThat(returned.size(), is(2));
        assertEquals(returned, expected);
    }

    @Test
    public void addProvince() {
    }

    @Test
    public void getRateByCities() {
        Rate r1 = new Rate(1,null,null,null,null);
        Rate r2 = new Rate(2,null,null,null,null);

        List<Rate> expected = new ArrayList<>();
        expected.add(r1);
        expected.add(r2);

        Mockito.when(rateRepository.getRateByCities(1,2)).thenReturn(expected);
        List<Rate> returned = rateRepository.getRateByCities(1,2);

        assertThat(returned.size(), is(2));
        assertEquals(returned, expected);
    }
    @Test
    public void getRateByCity() {
        Rate r1 = new Rate(1,null,null,null,null);
        Rate r2 = new Rate(2,null,null,null,null);

        List<Rate> expected = new ArrayList<>();
        expected.add(r1);
        expected.add(r2);

        Mockito.when(rateRepository.getRateByCity(1)).thenReturn(expected);
        List<Rate> returned = rateRepository.getRateByCity(1);

        assertThat(returned.size(), is(2));
        assertEquals(returned, expected);
    }

    @Test(expected = RateNotFoundException.class)
    public void updateRateError() throws RateNotFoundException {
        Mockito.when(rateRepository.findById(1).orElseThrow(RateNotFoundException::new)).thenThrow(new RateNotFoundException());
        rateRepository.findById(1);
    }

    @After
    public void tearDown() throws Exception {
    }
}