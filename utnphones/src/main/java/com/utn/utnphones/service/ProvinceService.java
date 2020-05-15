package com.utn.utnphones.service;

import com.utn.utnphones.model.City;
import com.utn.utnphones.model.Province;
import com.utn.utnphones.repository.CityRepository;
import com.utn.utnphones.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinceService {

    private final ProvinceRepository provinceRepository;

    @Autowired
    public ProvinceService(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    public List<Province> getAll() {
        return provinceRepository.findAll();
    }

    public void addProvince(final Province p) {
        provinceRepository.save(p);
    }
}
