package com.utn.utnphones.controller;

import com.utn.utnphones.model.Line;
import com.utn.utnphones.model.Province;
import com.utn.utnphones.service.LineService;
import com.utn.utnphones.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/line")
public class LineController {

    private final LineService lineService;

    @Autowired
    public LineController(LineService lineService) {
        this.lineService = lineService;
    }

    @GetMapping("/")
    public List<Line> getAll() {
        return lineService.getAll();
    }

    @PostMapping("/")
    public void addLine(@RequestBody Line l) {
        lineService.addLine(l);
    }
}
