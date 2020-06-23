package com.utn.utnphones.controller;

import com.utn.utnphones.dto.UpdateRateDto;
import com.utn.utnphones.exceptions.CityNotFoundException;
import com.utn.utnphones.exceptions.RateNotFoundException;
import com.utn.utnphones.model.Rate;
import com.utn.utnphones.service.CityService;
import com.utn.utnphones.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/rate")
public class RateController {

    private final RateService rateService;
    private final CityService cityService;

    @Autowired
    public RateController(RateService rateService, CityService cityService) {
        this.rateService = rateService;
        this.cityService = cityService;
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<List<Rate>> getAll() {
        List<Rate> rateList = rateService.getAll();

        if (rateList.size() > 0) {
            return ResponseEntity.ok(rateList);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity addRate(@RequestBody @Valid Rate r) {
        rateService.addProvince(r);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{fromCityId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<List<Rate>> getRateByCities(@PathVariable Integer fromCityId, @RequestParam(value = "toCityId", required = false) Integer toCityId) throws CityNotFoundException {

        if (!cityService.existsById(fromCityId))
            throw new CityNotFoundException();

        List<Rate> rateList;
        if (toCityId != null) {
            if (!cityService.existsById(toCityId))
                throw new CityNotFoundException();
            rateList = rateService.getRateByCities(fromCityId, toCityId);
        } else
            rateList = rateService.getRateByCities(fromCityId, null);

        if (rateList.size() > 0) {
            return ResponseEntity.ok(rateList);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @PutMapping("/{fromCityId}/{toCityId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity updateRate(@PathVariable Integer fromCityId, @PathVariable Integer toCityId, @RequestBody UpdateRateDto updateRateDto) throws CityNotFoundException, RateNotFoundException {
        if (cityService.existsById(fromCityId) && cityService.existsById(toCityId)) {
            rateService.updateRate(fromCityId, toCityId, updateRateDto);
        } else
            throw new CityNotFoundException();

        return ResponseEntity.ok().build();
    }
}
