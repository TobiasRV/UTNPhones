package com.utn.utnphones.service;

import com.utn.utnphones.dto.UpdateProvinceDto;
import com.utn.utnphones.exceptions.ProvinceNotFoundException;
import com.utn.utnphones.model.Province;
import com.utn.utnphones.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProvinceService {

    private final ProvinceRepository provinceRepository;

    @Autowired
    public ProvinceService(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    public List<Province> getAll() {
        return provinceRepository.findAll();
    }

    public void addProvince(final Province p) {
        provinceRepository.save(p);
    }

    public boolean existsById(Integer idProvince) {
        return provinceRepository.existsById(idProvince);
    }

    public Province getProvinceById(Integer provinceId) throws ProvinceNotFoundException {
        return provinceRepository.findById(provinceId).orElseThrow(ProvinceNotFoundException::new);
    }

    public void updateProvince(Integer provinceId, UpdateProvinceDto updateProvinceDto) throws ProvinceNotFoundException {
        Province oldProvince = provinceRepository.findById(provinceId).orElseThrow(ProvinceNotFoundException::new);

        if (updateProvinceDto.getProvinceName() != null)
            oldProvince.setProvinceName(updateProvinceDto.getProvinceName());

        provinceRepository.save(oldProvince);
    }

}
