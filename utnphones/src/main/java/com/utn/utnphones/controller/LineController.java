package com.utn.utnphones.controller;

import com.utn.utnphones.dto.LineAndQtyOfCallsDto;
import com.utn.utnphones.dto.UpdateLineDto;
import com.utn.utnphones.exceptions.CityNotFoundException;
import com.utn.utnphones.exceptions.LineNotFoundException;
import com.utn.utnphones.exceptions.UserNotFoundException;
import com.utn.utnphones.model.City;
import com.utn.utnphones.model.Line;
import com.utn.utnphones.service.CityService;
import com.utn.utnphones.service.LineService;
import com.utn.utnphones.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static com.utn.utnphones.security.Constants.SECRET_KEY;
import static com.utn.utnphones.utils.RestUtils.getLineLocation;

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
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<List<Line>> getAll() {
        List<Line> lineList = lineService.getAll();

        if (lineList.size() > 0) {
            return ResponseEntity.ok(lineList);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @GetMapping("/{lineId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT') or hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<Line> getLineById(@PathVariable Integer lineId, @RequestHeader String authorization) throws LineNotFoundException {
        Line line = lineService.getLineById(lineId);

        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).parseClaimsJws(authorization).getBody();

        List<String> authorities = (ArrayList) claims.get("authorities");

        if (authorities.get(0).equals("ROLE_CLIENT")) {
            if (!claims.getId().equals(line.getUser().getIdUser().toString())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }

        return ResponseEntity.ok(line);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT') or hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<List<Line>> getLinesByUserId(@PathVariable Integer userId, @RequestHeader String authorization) throws UserNotFoundException {
        if (!userService.existsById(userId))
            throw new UserNotFoundException();

        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).parseClaimsJws(authorization).getBody();

        List<String> authorities = (ArrayList) claims.get("authorities");

        if (authorities.get(0).equals("ROLE_CLIENT")) {
            if (!claims.getId().equals(userId.toString())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }

        List<Line> lineList = lineService.getLinesByUserId(userId);

        if (lineList.size() > 0) {
            return ResponseEntity.ok(lineList);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

    }


    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity addLine(@RequestBody @Valid Line l) {
        Line newLine = lineService.addLine(l);
        return ResponseEntity.created(getLineLocation(newLine)).build();
    }

    @PutMapping("/{lineId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity updateLine(@PathVariable Integer lineId, @RequestBody @Valid UpdateLineDto updateLineDto) throws LineNotFoundException, CityNotFoundException {
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

    @DeleteMapping("/{lineId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity deleteLine(@PathVariable Integer lineId) throws LineNotFoundException {
        lineService.deleteLine(lineId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/top10Destinations/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT') or hasRole('ROLE_EMPLOYEE')")
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

}
