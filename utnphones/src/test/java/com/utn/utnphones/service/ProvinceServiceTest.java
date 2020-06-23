package com.utn.utnphones.service;

import com.utn.utnphones.dto.UpdateLineDto;
import com.utn.utnphones.dto.UpdateProvinceDto;
import com.utn.utnphones.exceptions.ProvinceNotFoundException;
import com.utn.utnphones.model.Province;
import com.utn.utnphones.repository.ProvinceRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

public class ProvinceServiceTest {

    ProvinceService provinceService;

    @Mock
    ProvinceRepository provinceRepository;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        provinceService = new ProvinceService(provinceRepository);
    }

    @Test
    public void getAll() {
        Province p1 = new Province(1,null,null);
        Province p2 = new Province(2,null,null);

        List<Province> expected = new ArrayList<>();
        expected.add(p1);
        expected.add(p2);

        Mockito.when(provinceRepository.findAll()).thenReturn(expected);
        List<Province> returned = provinceService.getAll();

        assertThat(returned.size(), is(2));
        assertEquals(returned, expected);
    }

    @Test
    public void addProvince() {
        Province p1 = new Province(1,null,null);
        provinceService.addProvince(p1);
    }

    @Test
    public void existsById() {
        Mockito.when(provinceRepository.existsById(1)).thenReturn(true);
        boolean returned = provinceService.existsById(1);
        assertEquals(true,returned);
    }


    @Test
    public void getProvinceById() throws ProvinceNotFoundException {
        Province expected = new Province(1,null,null);
        Mockito.when(provinceRepository.findById(1)).thenReturn(Optional.of(expected));
        Province returned = provinceService.getProvinceById(1);

        assertEquals(expected,returned);
    }

    @Test(expected = ProvinceNotFoundException.class)
    public void getProvinceByIdError() throws ProvinceNotFoundException {
        Mockito.when(provinceRepository.findById(1).orElseThrow(ProvinceNotFoundException::new)).thenThrow(new ProvinceNotFoundException());
        provinceService.getProvinceById(1);
    }

    @Test
    public void updateProvince() throws ProvinceNotFoundException {
        Province p1 = new Province(1,null,null);
        Mockito.when(provinceRepository.findById(1)).thenReturn(Optional.of(p1));
        UpdateProvinceDto updateProvinceDto = new UpdateProvinceDto("Buenos Aires");
        provinceService.updateProvince(1,updateProvinceDto);
    }

    @Test
    public void updateProvinceDtoNull() throws ProvinceNotFoundException {
        Province p1 = new Province(1,null,null);
        Mockito.when(provinceRepository.findById(1)).thenReturn(Optional.of(p1));
        UpdateProvinceDto updateProvinceDto = new UpdateProvinceDto(null);
        provinceService.updateProvince(1,updateProvinceDto);
    }

    @Test(expected = ProvinceNotFoundException.class)
    public void updateProvinceError() throws ProvinceNotFoundException {
        Mockito.when(provinceRepository.findById(1).orElseThrow(ProvinceNotFoundException::new)).thenThrow(new ProvinceNotFoundException());
        UpdateProvinceDto updateProvinceDto = new UpdateProvinceDto("Buenos Aires");
        provinceService.updateProvince(1,updateProvinceDto);
    }

    @After
    public void tearDown() throws Exception {
    }
}