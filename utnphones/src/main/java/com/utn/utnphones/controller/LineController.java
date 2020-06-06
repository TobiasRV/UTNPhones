package com.utn.utnphones.controller;

import com.utn.utnphones.dto.LineAndQtyOfCallsDto;
import com.utn.utnphones.dto.UpdateLineDto;
import com.utn.utnphones.exceptions.CityNotFoundException;
import com.utn.utnphones.exceptions.LineNotFoundException;
import com.utn.utnphones.exceptions.UserNotFoundException;
import com.utn.utnphones.model.City;
import com.utn.utnphones.model.Line;
import com.utn.utnphones.model.User;
import com.utn.utnphones.service.CityService;
import com.utn.utnphones.service.LineService;
import com.utn.utnphones.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/line")
public class LineController {

    private final LineService lineService;
    private final UserService userService;
    private final CityService cityService;

    @Autowired
    public LineController(LineService lineService, UserService userService, CityService cityService) {
        this.lineService = lineService;
        this.userService = userService;
        this.cityService = cityService;
    }

    @GetMapping("/")
    public List<Line> getAll() {
        return lineService.getAll();
    }

    @GetMapping("/{lineId}")
    private ResponseEntity<Line> getLineById(@PathVariable Integer lineId) throws LineNotFoundException {
        return ResponseEntity.ok(lineService.getLineById(lineId));
    }

    @PostMapping("/")
    public ResponseEntity addLine(@RequestBody @Valid Line l) {
        Line newLine = lineService.addLine(l);
        return ResponseEntity.created(getLocation(newLine)).build();
    }

    @PutMapping("/{lineId}")
    public ResponseEntity updateLine(@PathVariable Integer lineId, @RequestBody @Valid UpdateLineDto updateLineDto) throws LineNotFoundException, UserNotFoundException, CityNotFoundException {
        if (!lineService.existsById(lineId))
            throw new LineNotFoundException();

        if (updateLineDto.getCityId() != null) {
            City city = cityService.getCityById(updateLineDto.getCityId());
            lineService.updateLine(lineId, updateLineDto, city);
            return ResponseEntity.ok().build();
        }

        lineService.updateLine(lineId, updateLineDto, null);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/top10Destinations/{userId}")
    public ResponseEntity<List<LineAndQtyOfCallsDto>> getTop10Destinations(@PathVariable Integer userId) throws UserNotFoundException {

        if (!userService.existsById(userId))
            throw new UserNotFoundException();

        List<LineAndQtyOfCallsDto> ll = lineService.getTop10Destinations(userId);

        if (ll.size() > 0) {
            return ResponseEntity.ok(ll);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    private URI getLocation(Line line) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{lineId}")
                .buildAndExpand(line.getIdLine())
                .toUri();
    }

}
