package com.utn.utnphones.controller;


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

import javax.validation.Valid;
import java.net.URI;
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
    public ResponseEntity addUser(@RequestBody @Valid User u) throws CityNotFoundException, ValidationException {

        if (!cityService.existsById(u.getCity().getIdCity()))
            throw new CityNotFoundException();

        User newUser = userService.addUser(u);
        return ResponseEntity.created(getLocation(newUser)).build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity updateUser(@PathVariable Integer userId, @RequestBody UpdateUserDto u) throws DataIntegrityViolationException, UserNotFoundException {
        userService.updateUser(userId, u);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable Integer userId) throws UserNotFoundException {
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
}

