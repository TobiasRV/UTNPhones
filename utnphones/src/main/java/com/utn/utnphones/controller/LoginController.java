package com.utn.utnphones.controller;

import com.utn.utnphones.dto.LoginRequestDto;
import com.utn.utnphones.exceptions.InvalidLoginException;
import com.utn.utnphones.exceptions.UserNotExistsException;
import com.utn.utnphones.exceptions.ValidationException;
import com.utn.utnphones.model.User;
import com.utn.utnphones.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class LoginController {

    UserController userController;
    SessionManager sessionManager;

    @Autowired
    public LoginController(UserController userController, SessionManager sessionManager) {
        this.userController = userController;
        this.sessionManager = sessionManager;
    }


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDto loginRequestDto) throws InvalidLoginException, ValidationException {
        ResponseEntity response;
        try {
            User u = userController.login(loginRequestDto.getUsername(), loginRequestDto.getPassword());
            String token = sessionManager.createSession(u);
            response = ResponseEntity.ok().headers(createHeaders(token)).build();
        } catch (UserNotExistsException e) {
            throw new InvalidLoginException(e);
        }
        return response;
    }


    @PostMapping("/logout")
    public ResponseEntity logout(@RequestHeader("Authorization") String token) {
        sessionManager.removeSession(token);
        return ResponseEntity.ok().build();
    }

    private HttpHeaders createHeaders(String token) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization", token);
        return responseHeaders;
    }


}