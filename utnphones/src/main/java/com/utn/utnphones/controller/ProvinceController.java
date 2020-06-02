package com.utn.utnphones.controller;

import com.utn.utnphones.model.City;
import com.utn.utnphones.model.Province;
import com.utn.utnphones.service.CityService;
import com.utn.utnphones.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/province")
public class ProvinceController {

    private final ProvinceService provinceService;

    @Autowired
    public ProvinceController(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @GetMapping("/")
    public List<Province> getAll() {
        return provinceService.getAll();
    }

    @PostMapping("/")
    public void addProvince(@RequestBody Province p) {
        provinceService.addProvince(p);
    }

    /*
    @GetMapping("/{province_id}")
    public ResponseEntity<Province>getProvinceByID(@PathVariable Integer province_id){
        Province result = provinceService.getById(province_id);

        if(result != null){
            return ResponseEntity.ok(result);
        }else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

    }

     */
}
