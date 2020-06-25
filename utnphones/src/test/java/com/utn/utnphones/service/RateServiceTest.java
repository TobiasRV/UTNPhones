package com.utn.utnphones.service;

import com.utn.utnphones.dto.UpdateRateDto;
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
        List<Rate> returned = rateService.getAll();

        assertThat(returned.size(), is(2));
        assertEquals(returned, expected);
    }

    @Test
    public void addProvince() {

    }

    @Test
    public void getRateByCities() {
        Rate r1 = new Rate(1,null,null,null,null);

        List<Rate> expected = new ArrayList<>();
        expected.add(r1);


        Mockito.when(rateRepository.getRateByCities(1,2)).thenReturn(expected);
        List<Rate> returned = rateService.getRateByCities(1,2);

        assertThat(returned.size(), is(1));
        assertEquals(returned, expected);
    }
    @Test
    public void getRateByCitiesToCityNull() {
        Rate r1 = new Rate(1,null,null,null,null);
        Rate r2 = new Rate(2,null,null,null,null);

        List<Rate> expected = new ArrayList<>();
        expected.add(r1);
        expected.add(r2);

        Mockito.when(rateRepository.getRateByCity(1)).thenReturn(expected);
        List<Rate> returned = rateService.getRateByCities(1,null);

        assertThat(returned.size(), is(2));
        assertEquals(returned, expected);
    }

    @Test
    public void updateRate() throws RateNotFoundException {
        UpdateRateDto updateRateDto = new UpdateRateDto(1.00,2.00);
        Rate r1 = new Rate(1,null,null,3.00,4.00);
        Mockito.when(rateRepository.getOnlyOneRateByCities(1,2)).thenReturn(r1);
        Mockito.when(rateRepository.existsByOriginCityAndDestinationCity(1,2)).thenReturn(true);
        rateService.updateRate(1,2,updateRateDto);
    }

    @Test
    public void updateRateCostPerMinuteNull() throws RateNotFoundException {
        UpdateRateDto updateRateDto = new UpdateRateDto(null,2.00);
        Rate r1 = new Rate(1,null,null,3.00,4.00);
        Mockito.when(rateRepository.getOnlyOneRateByCities(1,2)).thenReturn(r1);
        Mockito.when(rateRepository.existsByOriginCityAndDestinationCity(1,2)).thenReturn(true);
        rateService.updateRate(1,2,updateRateDto);
    }

    @Test
    public void updateRatePricePerMinuteNull() throws RateNotFoundException {
        UpdateRateDto updateRateDto = new UpdateRateDto(1.00,null);
        Rate r1 = new Rate(1,null,null,3.00,4.00);
        Mockito.when(rateRepository.getOnlyOneRateByCities(1,2)).thenReturn(r1);
        Mockito.when(rateRepository.existsByOriginCityAndDestinationCity(1,2)).thenReturn(true);
        rateService.updateRate(1,2,updateRateDto);
    }

    @Test(expected = RateNotFoundException.class)
    public void updateRateError() throws RateNotFoundException {
        UpdateRateDto updateRateDto = new UpdateRateDto(1.00,2.00);

        Mockito.when(rateRepository.existsByOriginCityAndDestinationCity(1,2)).thenReturn(false);
        rateService.updateRate(1,2,updateRateDto);
    }

    @After
    public void tearDown() throws Exception {
    }
}