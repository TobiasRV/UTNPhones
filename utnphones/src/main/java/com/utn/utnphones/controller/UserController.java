package com.utn.utnphones.controller;


import com.utn.utnphones.exceptions.UserNotExistsException;
import com.utn.utnphones.exceptions.UserNotFoundException;
import com.utn.utnphones.exceptions.ValidationException;
import com.utn.utnphones.model.User;
import com.utn.utnphones.service.UserService;
import com.utn.utnphones.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;


    @Autowired
    public UserController(UserService userService, SessionManager sessionManager) {
        this.userService = userService;
    }

    public User login(String username, String password) throws UserNotExistsException, ValidationException {
        if ((username != null) && (password != null)) {
            return userService.login(username, password);
        } else {
            throw new ValidationException("username and password must have a value");
        }
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

    //TODO lanzar excepcion si el usuario ingreso un id city que no existe.
    @PostMapping("/")
    public ResponseEntity addPerson(@RequestBody User u) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(u));
    }




}
