package com.utn.utnphones.controller;

import com.utn.utnphones.dto.LineAndQtyOfCallsDto;
import com.utn.utnphones.exceptions.LineNotFoundException;
import com.utn.utnphones.exceptions.UserNotExistsException;
import com.utn.utnphones.model.Call;
import com.utn.utnphones.model.Line;
import com.utn.utnphones.model.Province;
import com.utn.utnphones.service.LineService;
import com.utn.utnphones.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @PutMapping("/{lineId}")
    public ResponseEntity updateLine(@PathVariable Integer lineId, @Valid @RequestBody Line line) throws LineNotFoundException {

        return ResponseEntity.ok(lineService.updateLine(lineId, line));
    }


    @GetMapping("/top10Destinations/{userId}")
    public ResponseEntity<List<LineAndQtyOfCallsDto>> getTop10Destinations(@PathVariable Integer userId) throws UserNotExistsException {

        List<LineAndQtyOfCallsDto> ll = lineService.getTop10Destinations(userId);

        if (ll.size() > 0) {
            return ResponseEntity.ok(ll);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }



}
