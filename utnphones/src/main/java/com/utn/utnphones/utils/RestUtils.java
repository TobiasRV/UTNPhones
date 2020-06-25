package com.utn.utnphones.utils;

import com.utn.utnphones.model.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class RestUtils {

    public static URI getCityLocation(City city) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{cityId}")
                .buildAndExpand(city.getIdCity())
                .toUri();
    }

    public static URI getUserLocation(User user) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{userId}")
                .buildAndExpand(user.getIdUser())
                .toUri();
    }

    public static URI getLineLocation(Line line) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{lineId}")
                .buildAndExpand(line.getIdLine())
                .toUri();
    }

    public static URI getProvinceLocation(Province province) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{provinceId}")
                .buildAndExpand(province.getIdProvince())
                .toUri();
    }

    public static URI getRateLocation(Rate rate) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{rateId}")
                .buildAndExpand(rate.getIdRate())
                .toUri();
    }
}
