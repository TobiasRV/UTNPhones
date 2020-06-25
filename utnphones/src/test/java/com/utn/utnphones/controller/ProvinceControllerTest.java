package com.utn.utnphones.controller;

import com.utn.utnphones.dto.UpdateProvinceDto;
import com.utn.utnphones.exceptions.ProvinceNotFoundException;
import com.utn.utnphones.model.Province;
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

        Province p1 = new Province(1, "Buenos Aires", null);
        Province p2 = new Province(1, "Tucuman", null);

        List<Province> expected = new ArrayList<>();
        expected.add(p1);
        expected.add(p2);

        Mockito.when(provinceService.getAll()).thenReturn(expected);

        ResponseEntity<List<Province>> returned = controller.getAll();

        assertNotNull(returned);
        assertThat(returned.getBody().size(), is(2));
        assertEquals(expected, returned.getBody());
    }

    @Test
    public void getAllEmpty() {

        List<Province> expected = new ArrayList<>();

        when(provinceService.getAll()).thenReturn(expected);

        ResponseEntity<List<Province>> returned = controller.getAll();

        assertNotNull(returned);
        assertEquals(HttpStatus.NO_CONTENT, returned.getStatusCode());
    }

    @Test
    public void getProvinceById() throws ProvinceNotFoundException {
        Province expected = new Province(1,null,null);

        Mockito.when(provinceService.getProvinceById(1)).thenReturn(expected);

        ResponseEntity returned = controller.getProvinceById(1);

        assertNotNull(returned);
        assertEquals(expected,returned.getBody());
    }

    @Test
    public void addProvince(){

    }

    @Test
    public void updateProvince() throws ProvinceNotFoundException {
        UpdateProvinceDto updateProvinceDto = new UpdateProvinceDto("Buenos Aires");

        Mockito.when(provinceService.existsById(1)).thenReturn(true);

        ResponseEntity returned = controller.updateProvince(1,updateProvinceDto);

        assertNotNull(returned);
        assertEquals(HttpStatus.OK, returned.getStatusCode());
    }

    @Test(expected = ProvinceNotFoundException.class)
    public void updateProvinceError() throws ProvinceNotFoundException {
        UpdateProvinceDto updateProvinceDto = new UpdateProvinceDto("Buenos Aires");
        Mockito.when(provinceService.existsById(1)).thenReturn(false);
        controller.updateProvince(1,updateProvinceDto);
    }

}