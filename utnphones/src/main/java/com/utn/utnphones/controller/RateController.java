package com.utn.utnphones.controller;

import com.utn.utnphones.exceptions.CityNotFoundException;
import com.utn.utnphones.model.Call;
import com.utn.utnphones.model.Province;
import com.utn.utnphones.model.Rate;
import com.utn.utnphones.service.CityService;
import com.utn.utnphones.service.ProvinceService;
import com.utn.utnphones.service.RateService;
import org.apache.coyote.Response;
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
    private final CityService cityService;

    @Autowired
    public RateController(RateService rateService, CityService cityService) {
        this.rateService = rateService;
        this.cityService = cityService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Rate>> getAll() {
        List<Rate> rateList = rateService.getAll();

        if (rateList.size() > 0) {
            return ResponseEntity.ok(rateList);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @PostMapping("/")
    public ResponseEntity addRate(@RequestBody @Valid Rate r) {
        rateService.addProvince(r);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{fromCityId}")
    public ResponseEntity<List<Rate>> getRate(@PathVariable Integer fromCityId, @RequestParam(value = "toCityId", required = false) Integer toCityId) throws CityNotFoundException {


        if (!cityService.existsById(fromCityId))
            throw new CityNotFoundException();

        if (toCityId != null) {
            if (!cityService.existsById(toCityId))
                throw new CityNotFoundException();
            return ResponseEntity.ok(rateService.getRateByCities(fromCityId, toCityId));
        } else //TODO EL NULL ENVIADO POR PARAMETRO ES REDUNDANTE PORQUE SI NO SE ENVIO "TOCITYID" POR URL YA ES NULL
            return ResponseEntity.ok(rateService.getRateByCities(fromCityId, null));

    }
}
