package com.utn.utnphones.service;


import com.utn.utnphones.exceptions.CityNotFoundException;
import com.utn.utnphones.exceptions.UserNotFoundException;
import com.utn.utnphones.model.City;
import com.utn.utnphones.model.User;
import com.utn.utnphones.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    private final CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> getAll() {
        return cityRepository.findAll();
    }

    public void addCity(final City c) {
        cityRepository.save(c);
    }

    public boolean existsById(Integer idCity) {
        return cityRepository.existsById(idCity);
    }

    public City getCityById(Integer cityId) throws CityNotFoundException {
        return cityRepository.findById(cityId).orElseThrow(CityNotFoundException::new);
    }
}
