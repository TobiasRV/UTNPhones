package com.utn.utnphones.service;

import com.utn.utnphones.dto.UpdateRateDto;
import com.utn.utnphones.exceptions.CityNotFoundException;
import com.utn.utnphones.exceptions.RateNotFoundException;
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

    public Rate addRate(final Rate r) {
        return rateRepository.save(r);
    }

    public List<Rate> getRateByCities(Integer fromCityId, Integer toCityId) {
        if (toCityId != null)
            return rateRepository.getRateByCities(fromCityId, toCityId);
        else
            return rateRepository.getRateByCity(fromCityId);
    }

    public void updateRate(Integer fromCityId, Integer toCityId, UpdateRateDto updateRateDto) throws RateNotFoundException {
        if (!rateRepository.existsByOriginCityAndDestinationCity(fromCityId, toCityId))
            throw new RateNotFoundException();
        Rate oldRate = rateRepository.getOnlyOneRateByCities(fromCityId, toCityId);

        if (updateRateDto.getCostPerMinute() != null)
            oldRate.setCostPerMinute(updateRateDto.getCostPerMinute());
        if (updateRateDto.getPricePerMinute() != null)
            oldRate.setPricePerMinute(updateRateDto.getPricePerMinute());

        rateRepository.save(oldRate);
    }
}
