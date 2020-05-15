package com.utn.utnphones.controller;

import com.utn.utnphones.model.Province;
import com.utn.utnphones.model.Rate;
import com.utn.utnphones.service.ProvinceService;
import com.utn.utnphones.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rate")
public class RateController {

    private final RateService rateService;

    @Autowired
    public RateController(RateService rateService) {
        this.rateService = rateService;
    }

    @GetMapping("/")
    public List<Rate> getAll() {
        return rateService.getAll();
    }

    @PostMapping("/")
    public void addRate(@RequestBody Rate r) {
        rateService.addProvince(r);
    }
}
