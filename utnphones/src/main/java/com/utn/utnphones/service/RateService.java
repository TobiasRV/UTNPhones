package com.utn.utnphones.service;

import com.utn.utnphones.exceptions.CityNotFoundException;
import com.utn.utnphones.model.Province;
import com.utn.utnphones.model.Rate;
import com.utn.utnphones.repository.ProvinceRepository;
import com.utn.utnphones.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RateService {

    private final RateRepository rateRepository;

    @Autowired
    public RateService(RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    public List<Rate> getAll() {
        return rateRepository.findAll();
    }

    public void addProvince(final Rate r) {
        rateRepository.save(r);
    }

    public List<Rate> getRateByCities(Integer fromCityId, Integer toCityId) throws CityNotFoundException {
        if(rateRepository.existsById(fromCityId) && rateRepository.existsById(toCityId)){
            return rateRepository.getRateByCities(fromCityId, toCityId);
        }else{
            throw new CityNotFoundException();
        }
    }
}
