package com.utn.utnphones.controller;


import com.utn.utnphones.dto.UpdateUserDto;
import com.utn.utnphones.dto.UserLoginDto;
import com.utn.utnphones.exceptions.CityNotFoundException;
import com.utn.utnphones.exceptions.InvalidLoginException;
import com.utn.utnphones.exceptions.UserNotFoundException;
import com.utn.utnphones.exceptions.ValidationException;
import com.utn.utnphones.model.User;
import com.utn.utnphones.security.SessionManager;
import com.utn.utnphones.service.CityService;
import com.utn.utnphones.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static com.utn.utnphones.security.Constants.SECRET_KEY;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final CityService cityService;
    private final SessionManager sessionManager;

    @Autowired
    public UserController(UserService userService, CityService cityService, SessionManager sessionManager) {
        this.userService = userService;
        this.cityService = cityService;
        this.sessionManager = sessionManager;
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginDto> login(@RequestParam("username") String username, @RequestParam("password") String password) throws InvalidLoginException, ValidationException, UserNotFoundException {
        UserLoginDto userDto = new UserLoginDto();
        if (username != null && password != null) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            User user = userService.getUserByUsername(username);
            if (!bCryptPasswordEncoder.matches(password, user.getPassword()))
                throw new InvalidLoginException();

            String token = userService.getJWTToken(user.getIdUser(), username, user.getRole(), sessionManager);
            userDto.setUserId(user.getIdUser());
            userDto.setUsername(username);
            userDto.setToken(token);

        } else
            throw new ValidationException("Fields username and password must not be null");

        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<List<User>> getAll() {

        List<User> userList = userService.getAll();

        if (userList.size() > 0) {
            return ResponseEntity.ok(userList);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public ResponseEntity<User> getUserById(@PathVariable Integer userId, @RequestHeader String authorization) throws UserNotFoundException {
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).parseClaimsJws(authorization).getBody();

        List<String> authorities = (ArrayList) claims.get("authorities");

        if (authorities.get(0).equals("ROLE_CLIENT")) {
            if (!claims.getId().equals(userId.toString())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }

        User u = userService.getUserById(userId);

        return ResponseEntity.ok(u);

    }

    @PostMapping("/")
    public ResponseEntity addUser(@RequestBody @Valid User user) throws CityNotFoundException, ValidationException {

        if (!cityService.existsById(user.getCity().getIdCity()))
            throw new CityNotFoundException();

        User newUser = userService.addUser(user);
        return ResponseEntity.created(getLocation(newUser)).build();
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public ResponseEntity updateUser(@PathVariable Integer userId, @RequestBody UpdateUserDto u, @RequestHeader String authorization) throws DataIntegrityViolationException, UserNotFoundException {
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).parseClaimsJws(authorization).getBody();

        List<String> authorities = (ArrayList) claims.get("authorities");

        if (authorities.get(0).equals("ROLE_CLIENT")) {
            if (!claims.getId().equals(userId.toString())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }
        userService.updateUser(userId, u);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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

