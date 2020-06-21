package com.utn.utnphones.service;

import com.utn.utnphones.exceptions.ProvinceNotFoundException;
import com.utn.utnphones.model.Province;
import com.utn.utnphones.repository.ProvinceRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
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
        List<Province> returned = provinceRepository.findAll();

        assertThat(returned.size(), is(2));
        assertEquals(returned, expected);
    }

    @Test
    public void addProvince() {
    }

    @Test
    public void existsByIdTrue() {
        Mockito.when(provinceRepository.existsById(1)).thenReturn(true);
        boolean returned = provinceRepository.existsById(1);
        assertEquals(true,returned);
    }
    @Test
    public void existsByIdFalse() {
        Mockito.when(provinceRepository.existsById(233)).thenReturn(false);
        boolean returned = provinceRepository.existsById(233);
        assertEquals(false,returned);
    }

    @Test
    public void getProvinceById() {
        Province expected = new Province(1,null,null);
        Mockito.when(provinceRepository.findById(1)).thenReturn(java.util.Optional.of(expected));
        Optional<Province> returned = provinceRepository.findById(1);

        assertEquals(expected,returned.get());
    }

    @Test(expected = ProvinceNotFoundException.class)
    public void getProvinceByIdError() throws ProvinceNotFoundException {
        Mockito.when(provinceRepository.findById(1).orElseThrow(ProvinceNotFoundException::new)).thenThrow(new ProvinceNotFoundException());
        provinceRepository.findById(1);
    }

    @Test(expected = ProvinceNotFoundException.class)
    public void updateProvinceError() throws ProvinceNotFoundException {
        Mockito.when(provinceRepository.findById(1).orElseThrow(ProvinceNotFoundException::new)).thenThrow(new ProvinceNotFoundException());
        provinceRepository.findById(1);
    }
}