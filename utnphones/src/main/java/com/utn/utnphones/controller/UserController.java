package com.utn.utnphones.controller;


import com.utn.utnphones.dto.ReturnUserDto;
import com.utn.utnphones.dto.UpdateUserDto;
import com.utn.utnphones.exceptions.*;
import com.utn.utnphones.model.User;
import com.utn.utnphones.service.CityService;
import com.utn.utnphones.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final CityService cityService;

    @Autowired
    public UserController(UserService userService, CityService cityService) {
        this.userService = userService;
        this.cityService = cityService;
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> getAll() {

        List<User> userList = userService.getAll();

        if (userList.size() > 0) {
            return ResponseEntity.ok(userList);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Integer userId) throws UserNotFoundException {
        User u = userService.getUserById(userId);
        return ResponseEntity.ok(u);
    }

    @PostMapping("/")
    public ResponseEntity addUser(@RequestBody User u) throws UserAlreadyExistsException, CityNotExistsException {

        if (!cityService.existsById(u.getCity().getIdCity()))
            throw new CityNotExistsException();

        User newUser = userService.addUser(u);
        return ResponseEntity.created(getLocation(newUser)).build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity updateUser(@PathVariable Integer userId, @RequestBody UpdateUserDto u) throws UserNotExistsException, DataIntegrityViolationException {
        return ResponseEntity.ok(userService.updateUser(userId, u));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable Integer userId) throws UserNotExistsException {
        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //returns the location of the last inserted user through a header
    private URI getLocation(User user) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{userId}")
                .buildAndExpand(user.getIdUser())
                .toUri();
    }


    /*
        El endpoint quedaria localhost:8080/api/user/dni y en los query param enviar condicion que debe ser  0 para los dni pares y 1 para los impares, en caso de enviar otro numero tira error
     */
    @GetMapping("/dni")
    public ResponseEntity<List<ReturnUserDto>> getUsersByDniEvenOrOdd(@RequestParam(value = "condition", required = true) Integer condition) throws ParameterNotValidException {

        if (condition == 0 || condition == 1) {
            List<ReturnUserDto> response = new ArrayList<ReturnUserDto>();
            for (User user : userService.getUsersByDniEvenOrOdd(condition)) {
                ReturnUserDto dto = new ReturnUserDto();
                dto.setId(user.getIdUser());
                dto.setName(user.getName());
                dto.setLastname(user.getLastname());
                dto.setUsername(user.getUsername());
                dto.setDni(user.getDni());
                dto.setStatus(user.getStatus());
                dto.setCity(user.getCity());
                dto.setLineList(user.getLines());
                response.add(dto);
            }
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }
}

