package com.utn.utnphones.controller;

import com.utn.utnphones.dto.UpdateProvinceDto;
import com.utn.utnphones.exceptions.ProvinceNotFoundException;
import com.utn.utnphones.model.Province;
import com.utn.utnphones.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.utn.utnphones.utils.RestUtils.getCityLocation;
import static com.utn.utnphones.utils.RestUtils.getProvinceLocation;

@RestController
@RequestMapping("/api/province")
public class ProvinceController {

    private final ProvinceService provinceService;

    @Autowired
    public ProvinceController(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Province>> getAll() {
        List<Province> provinceList = provinceService.getAll();

        if (provinceList.size() > 0) {
            return ResponseEntity.ok(provinceList);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity addProvince(@RequestBody @Valid Province p) {
        Province newProvince = provinceService.addProvince(p);
        return ResponseEntity.created(getProvinceLocation(newProvince)).build();
    }

    @GetMapping("/{provinceId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Province> getProvinceById(@PathVariable Integer provinceId) throws ProvinceNotFoundException {
        return ResponseEntity.ok(provinceService.getProvinceById(provinceId));
    }

    @PutMapping("/{provinceId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity updateProvince(@PathVariable Integer provinceId, @RequestBody UpdateProvinceDto updateProvinceDto) throws ProvinceNotFoundException {
        if (!provinceService.existsById(provinceId))
            throw new ProvinceNotFoundException();

        provinceService.updateProvince(provinceId, updateProvinceDto);

        return ResponseEntity.ok().build();
    }

}
