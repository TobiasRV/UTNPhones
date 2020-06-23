package com.utn.utnphones.service;


import com.utn.utnphones.dto.UpdateCityDto;
import com.utn.utnphones.exceptions.CityNotFoundException;
import com.utn.utnphones.model.City;
import com.utn.utnphones.model.Province;
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

    public City addCity(final City c) {
        return cityRepository.save(c);
    }

    public boolean existsById(Integer idCity) {
        return cityRepository.existsById(idCity);
    }

    public City getCityById(Integer cityId) throws CityNotFoundException {
        return cityRepository.findById(cityId).orElseThrow(CityNotFoundException::new);
    }

    public void updateCity(Integer cityId, UpdateCityDto updateCityDtoCity, Province province) throws CityNotFoundException {
        City oldCity = cityRepository.findById(cityId).orElseThrow(CityNotFoundException::new);

        if (province != null)
            oldCity.setProvince(province);
        if (updateCityDtoCity.getCityName() != null)
            oldCity.setCityName(updateCityDtoCity.getCityName());
        if (updateCityDtoCity.getPrefix() != null)
            oldCity.setPrefix(updateCityDtoCity.getPrefix());

        cityRepository.save(oldCity);
    }
}
