package com.utn.utnphones.controller;

import com.utn.utnphones.exceptions.CityNotFoundException;
import com.utn.utnphones.model.Call;
import com.utn.utnphones.model.Province;
import com.utn.utnphones.model.Rate;
import com.utn.utnphones.service.ProvinceService;
import com.utn.utnphones.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/rate")
public class RateController {

    private final RateService rateService;

    @Autowired
    public RateController(RateService rateService) {
        this.rateService = rateService;
    }

    //@GetMapping("/")
    //public List<Rate> getAll() {
    // return rateService.getAll();
    //}

    @PostMapping("/")
    public void addRate(@RequestBody @Valid Rate r) {
        rateService.addProvince(r);
    }

    @GetMapping("/")
    public ResponseEntity<List<Rate>> getRate(@RequestParam(value = "fromCityId", required = false) Integer fromCityId, @RequestParam(value = "toCityId", required = false) Integer toCityId) throws CityNotFoundException {

        List<Rate> lr;
        if (fromCityId == null || toCityId == null) {
            lr = rateService.getAll();
        } else {
            lr = rateService.getRateByCities(fromCityId, toCityId);
        }

        if (lr.size() > 0) {
            return ResponseEntity.ok(lr);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }
}
