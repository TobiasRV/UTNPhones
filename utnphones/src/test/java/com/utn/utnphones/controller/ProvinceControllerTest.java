package com.utn.utnphones.controller;

import com.utn.utnphones.model.Bill;
import com.utn.utnphones.model.Province;
import com.utn.utnphones.service.ProvinceService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ProvinceControllerTest {

    ProvinceController controller;

    @Mock
    ProvinceService provinceService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        controller = new ProvinceController(provinceService);
    }

    @Test
    public void getAll() {

        Province p1 = new Province(1,"Buenos Aires",null);
        Province p2 = new Province(1,"Tucuman",null);

        List<Province> expected = new ArrayList<>();
        expected.add(p1);
        expected.add(p2);

        Mockito.when(provinceService.getAll()).thenReturn(expected);

        List<Province> returned = provinceService.getAll();

        if (returned != null) {
            assertThat(returned.size(), is(2));
        }

        assertEquals(returned, expected);
    }

    @Test
    public void getAllEmpty() {
        List<Province> expected = new ArrayList<>();

        when(provinceService.getAll()).thenReturn(expected);

        List<Province> returned = provinceService.getAll();

        assertThat(returned.size(), is(0));
    }

    // TODO Hacer test
    @Test
    public void addProvince() {
    }
}