package com.utn.utnphones.controller;


import com.utn.utnphones.model.User;
import com.utn.utnphones.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/")
    public List<User> getAll() {
        return userService.getAll();
    }

    @PostMapping("/")
    public void addPerson(@RequestBody User u) {
        userService.addUser(u);
    }


}
